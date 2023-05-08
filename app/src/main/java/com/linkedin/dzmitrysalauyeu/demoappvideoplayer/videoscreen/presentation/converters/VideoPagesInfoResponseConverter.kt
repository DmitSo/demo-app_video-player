package com.linkedin.dzmitrysalauyeu.demoappvideoplayer.videoscreen.presentation.converters

import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.videoscreen.data.dto.VideoPagesInfoResponse
import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.videoscreen.domain.pojo.VideoPagesInfoModel

/**
 * I suppose it should be working as some DTO. But as long as model doesn't differ from what is used at the screen,
 * I have decided to not to make over-boilerplate and use it at VM and view sides
 */
fun VideoPagesInfoResponse.toModel() = VideoPagesInfoModel(
    results.singleVideoUrl,
    results.splitVVideoUrl,
    results.splitHVideoUrl,
    results.srcVideoUrl
)
