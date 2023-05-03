package com.linkedin.dzmitrysalauyeu.demoappvideoplayer.videoscreen.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

open class BaseViewModel : ViewModel() {

    val viewModelDisposables = CompositeDisposable()

    private val _viewModelErrorEvent = SingleLiveMutableEvent<Throwable>()
    val viewModelErrorEvent: LiveData<Throwable> = _viewModelErrorEvent

    protected fun <T : Any> makeCall(
        single: Single<T>,
        onSuccess: (T) -> Unit,
        onError: (throwable: Throwable) -> Boolean = { false } // isHandled
    ) {
        viewModelDisposables.add(
            single
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { onSuccess(it) },
                    { if (!onError(it)) _viewModelErrorEvent.postValue(it) }
                )
        )
    }
}