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
    val infoApiService: VideoPagesInfoApiService
): BaseViewModel() {

    private val _videoPagesInfo = MutableLiveData<VideoPagesInfoModel>()
    val videoPagesInfo: LiveData<VideoPagesInfoModel> = _videoPagesInfo

    fun requestVideoPagesInfo() {
        makeCall(infoApiService.getVideoPagesInfoResult(), {

            // TODO
            _videoPagesInfo.postValue(it.body()?.toModel())
        })
    }
}