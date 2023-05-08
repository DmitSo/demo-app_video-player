package com.linkedin.dzmitrysalauyeu.demoappvideoplayer.videoscreen.data.repository

import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.common.utils.Resource
import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.common.utils.mapNetworkError
import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.videoscreen.data.apiservice.VideoPagesInfoApiService
import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.videoscreen.domain.pojo.VideoPagesInfoModel
import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.videoscreen.presentation.converters.toModel
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class VideoPageInfoRepository @Inject constructor(
    private val infoApi: VideoPagesInfoApiService
) {

    fun getVideoPagesInfo(): Flowable<Resource<VideoPagesInfoModel>> =
        infoApi.getVideoPagesInfoResult().toFlowable()
            .map {
                if (it.isSuccessful.not()) return@map Resource.Error(mapNetworkError(it.errorBody()))

                return@map Resource.SuccessRemote(it.body()?.toModel())
            }.startWithItem(Resource.Loading())
}