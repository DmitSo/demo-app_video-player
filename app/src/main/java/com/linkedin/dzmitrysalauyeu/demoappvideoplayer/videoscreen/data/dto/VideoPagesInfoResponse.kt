package com.linkedin.dzmitrysalauyeu.demoappvideoplayer.videoscreen.data.dto

import com.google.gson.annotations.SerializedName

data class VideoPagesInfoResponse(

    @SerializedName("a")
    val a: String,

    @SerializedName("task_id")
    val taskId: Long,

    @SerializedName("status")
    val status: Long,

    @SerializedName("results")
    val results: VideoPagesInfoResult
)

data class VideoPagesInfoResult(

    @SerializedName("single")
    val singleVideoUrl: String,

    @SerializedName("split_v")
    val splitVVideoUrl: String,

    @SerializedName("split_h")
    val splitHVideoUrl: String,

    @SerializedName("src")
    val srcVideoUrl: String,

    @SerializedName("preview_image")
    val previewImageUrl: String
)