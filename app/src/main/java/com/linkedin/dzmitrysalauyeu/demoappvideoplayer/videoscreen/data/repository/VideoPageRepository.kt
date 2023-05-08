package com.linkedin.dzmitrysalauyeu.demoappvideoplayer.videoscreen.data.repository

import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.videoscreen.data.apiservice.VideoPageApiService
import javax.inject.Inject

/**
 * Self-roast: haven't been using ExoPlayer so it could be removed at all (with the service itself)
 */
class VideoPageRepository @Inject constructor(
    val videoPagesApiService: VideoPageApiService
) {

    fun loadVideoFromUrl(url: String) = videoPagesApiService.loadVideoFromUrl(url)
}