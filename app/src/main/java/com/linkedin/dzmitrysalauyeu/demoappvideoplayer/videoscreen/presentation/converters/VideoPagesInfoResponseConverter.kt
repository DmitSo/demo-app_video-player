package com.linkedin.dzmitrysalauyeu.demoappvideoplayer.videoscreen.presentation.converters

import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.videoscreen.data.dto.VideoPagesInfoResponse
import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.videoscreen.domain.pojo.VideoPagesInfoModel

fun VideoPagesInfoResponse.toModel() = VideoPagesInfoModel(
    results.singleVideoUrl,
    results.splitVVideoUrl,
    results.splitHVideoUrl,
    results.srcVideoUrl
)
