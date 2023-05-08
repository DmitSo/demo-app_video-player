package com.linkedin.dzmitrysalauyeu.demoappvideoplayer.videoscreen.presentation.pagescontainer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.common.utils.ScreenGeneralState
import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.videoscreen.presentation.base.BaseViewModel
import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.videoscreen.data.repository.VideoPageInfoRepository
import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.videoscreen.domain.pojo.VideoPagesInfoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VideoPagesContainerViewModel @Inject constructor(
    private val videoPageInfoRepository: VideoPageInfoRepository
) : BaseViewModel() {

    private val _videoPagesInfo = MutableLiveData<VideoPagesInfoModel>()
    val videoPagesInfo: LiveData<VideoPagesInfoModel> = _videoPagesInfo

    private val _playbackLastTimeCodeMs = mutableMapOf<Int, Long>()

    var activeVideoId: Int = 0
        private set

    fun requestVideoPagesInfo() {
        makeCall(
            videoPageInfoRepository.getVideoPagesInfo(),
            onSuccess = { if (_videoPagesInfo.value != it) _videoPagesInfo.postValue(it) },
        )
    }

    fun saveTimeCode(videoId: Int, timeCodeMs: Long, videoDurationMs: Long) {
        activeVideoId = videoId
        _playbackLastTimeCodeMs[videoId] = timeCodeMs
            .takeIf { videoDurationMs - it > MAX_VIDEO_FINISH_OFFSET_MS }
            ?: DEFAULT_VIDEO_START_TIMESTAMP
    }

    fun getLastTimeCode(videoId: Int) = _playbackLastTimeCodeMs[videoId] ?: kotlin.run{
        saveTimeCode(videoId, DEFAULT_VIDEO_START_TIMESTAMP, Long.MAX_VALUE)
        DEFAULT_VIDEO_START_TIMESTAMP
    }

    fun displayVideoNotFoundError() {
        _screenGeneralState.postValue(ScreenGeneralState.ErrorScreenGeneralState(Exception("123")))
    }

    companion object {
        private const val DEFAULT_VIDEO_START_TIMESTAMP = 0L
        private const val MAX_VIDEO_FINISH_OFFSET_MS = 2500L
    }
}