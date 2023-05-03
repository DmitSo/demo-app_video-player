package com.linkedin.dzmitrysalauyeu.demoappvideoplayer.videoscreen.data.repository

import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.videoscreen.data.apiservice.VideoPageApiService
import javax.inject.Inject

class VideoPageRepository @Inject constructor(
    val videoPagesApiService: VideoPageApiService
) {

    fun loadVideoFromUrl(url: String) = videoPagesApiService.loadVideoFromUrl(url)
}