package com.linkedin.dzmitrysalauyeu.demoappvideoplayer.videoscreen.presentation.pagescontainer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.videoscreen.presentation.base.BaseViewModel
import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.videoscreen.data.apiservice.VideoPagesInfoApiService
import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.videoscreen.domain.pojo.VideoPagesInfoModel
import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.videoscreen.presentation.converters.toModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VideoPagesContainerViewModel @Inject constructor(
    val infoApiService: VideoPagesInfoApiService    // Self-roast: private
): BaseViewModel() {

    private val _videoPagesInfo = MutableLiveData<VideoPagesInfoModel>()
    val videoPagesInfo: LiveData<VideoPagesInfoModel> = _videoPagesInfo

    fun requestVideoPagesInfo() {
        /**
         * Self-roast: check _videoPagesInfo.value == null first to not to make too much requests if data present.
         * Or you could make caching mechanism where you can check if new response are the same as saved or not
         * AND it is not welcomed to use apiService directly. For this task it might be ok BUT
         * VM shouldn't know about service, use repository instead
         * AND Don't forget to make error handling here.
         */
        makeCall(infoApiService.getVideoPagesInfoResult(), {

            // TODO
            _videoPagesInfo.postValue(it.body()?.toModel())
        })
    }
}