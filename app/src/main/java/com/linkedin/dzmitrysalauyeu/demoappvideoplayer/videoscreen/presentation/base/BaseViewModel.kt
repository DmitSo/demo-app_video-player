package com.linkedin.dzmitrysalauyeu.demoappvideoplayer.videoscreen.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.common.utils.Resource
import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.common.utils.ScreenGeneralState
import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.common.utils.ScreenGeneralState.ErrorScreenGeneralState
import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.common.utils.ScreenGeneralState.LoadingScreenGeneralState
import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.common.utils.ScreenGeneralState.NormalScreenGeneralState
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

open class BaseViewModel : ViewModel() {

    private val viewModelDisposables = CompositeDisposable()

    protected val _screenGeneralState = SingleLiveMutableEvent<ScreenGeneralState>()
    val screenGeneralState: LiveData<ScreenGeneralState> = _screenGeneralState

    fun resetError() {
        if (_screenGeneralState.value is ErrorScreenGeneralState) {
            _screenGeneralState.value = NormalScreenGeneralState
        }
    }

    protected fun <T : Any> makeCall(
        single: Single<T>,
        onLoading: () -> Unit = {},
        onSuccess: (T) -> Unit,
        onError: (throwable: Throwable) -> Boolean = { false } // isHandled
    ) {
        viewModelDisposables.add(
            single
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    _screenGeneralState.postValue(LoadingScreenGeneralState)
                    onLoading()
                }
                .subscribe(
                    {
                        _screenGeneralState.postValue(NormalScreenGeneralState)
                        onSuccess(it)
                    },
                    { if (!onError(it)) _screenGeneralState.postValue(ErrorScreenGeneralState(it)) }
                )
        )
    }

    protected fun <T : Any> makeCall(
        flowable: Flowable<Resource<T>>,
        onLoading: () -> Unit = {},
        onSuccess: (T) -> Unit,
        onError: (throwable: Throwable) -> Boolean = { false } // isHandled
    ) {
        viewModelDisposables.add(
            flowable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        when (it) {
                            is Resource.Loading -> {
                                _screenGeneralState.postValue(LoadingScreenGeneralState)
                                onLoading()
                            }
                            is Resource.SuccessLocal -> {
                                _screenGeneralState.postValue(NormalScreenGeneralState)
                                it.data?.let(onSuccess)
                            }
                            is Resource.SuccessRemote -> {
                                _screenGeneralState.postValue(NormalScreenGeneralState)
                                it.data?.let(onSuccess)
                            }
                            is Resource.Error -> throw it.throwable!!
                        }
                    },
                    { if (!onError(it)) _screenGeneralState.postValue(ErrorScreenGeneralState(it)) }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        viewModelDisposables.clear()
    }
}