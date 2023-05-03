package com.linkedin.dzmitrysalauyeu.demoappvideoplayer.videoscreen.data.apiservice

import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.videoscreen.data.dto.VideoPagesInfoResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET

interface VideoPagesInfoApiService {

    @GET("test/item")
    fun getVideoPagesInfoResult(): Single<Response<VideoPagesInfoResponse>>
}