package com.linkedin.dzmitrysalauyeu.demoappvideoplayer.videoscreen.data.apiservice

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Streaming

interface VideoPageApiService {

    @GET("{url}")
    @Streaming
    fun loadVideoFromUrl(@Path("url", encoded = false) url: String): Call<ResponseBody>
}