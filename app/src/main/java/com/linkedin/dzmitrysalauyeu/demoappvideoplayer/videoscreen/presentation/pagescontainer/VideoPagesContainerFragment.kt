package com.linkedin.dzmitrysalauyeu.demoappvideoplayer.videoscreen.presentation.pagescontainer

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.BuildConfig
import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.common.utils.ScreenGeneralState
import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.common.utils.ScreenGeneralState.ErrorScreenGeneralState
import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.common.utils.ScreenGeneralState.LoadingScreenGeneralState
import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.common.utils.ScreenGeneralState.NormalScreenGeneralState
import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.common.utils.makeAlert
import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.common.utils.makeErrorAlert
import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.databinding.FragmentVideoPagesContainerBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class VideoPagesContainerFragment : Fragment() {

    private val viewModel: VideoPagesContainerViewModel by viewModels()
    private lateinit var binding: FragmentVideoPagesContainerBinding

    private var activeVideoContainer: ViewGroup? = null
    private var pageRecycler: RecyclerView? = null

    private val pagerAdapter = VideoPagerAdapter()
    private var activePagerPosition: Int? = null
    private var pageChangeCallback: ViewPager2.OnPageChangeCallback? = null

    private lateinit var videoSurfaceView: PlayerView
    private lateinit var videoPlayer: SimpleExoPlayer

    private lateinit var videoDataSourceFactory: DefaultDataSourceFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoPagesContainerBinding.inflate(inflater, container, false)    
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModelChanges()
        binding.videoPagerSwipeRefresh.setOnRefreshListener { viewModel.requestVideoPagesInfo() }
        viewModel.requestVideoPagesInfo()
    }

    override fun onStart() {
        super.onStart()
        initVideoPlayer()
        setupVideoPager()
        viewModel.activeVideoId.takeIf { it < pagerAdapter.videoUrlItems.size }?.let {
            pageRecycler?.scrollToPosition(it)
        }
        restoreStateForCurrentPage()
    }

    override fun onStop() {
        super.onStop()
        savePlaybackState()
        pageChangeCallback?.let { binding.videoPager.unregisterOnPageChangeCallback(it) }
        videoSurfaceView.player = null
        videoPlayer.release()
    }

    private fun initVideoPlayer() {
        videoSurfaceView = PlayerView(requireContext()).apply {
            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
        }
        videoDataSourceFactory = DefaultDataSourceFactory(
            requireContext(),
            Util.getUserAgent(requireContext(), BuildConfig.APPLICATION_ID)
        )
        videoPlayer = SimpleExoPlayer.Builder(requireContext())
            .build()
    }

    private fun setupVideoPager() {
        pageRecycler = (binding.videoPager.getChildAt(0) as? RecyclerView) ?: return
        val callback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                savePlaybackState()
                activeVideoContainer?.let { cleanView(it) }
                activeVideoContainer = null

                // get corresponding view to put video view in
                // by getting viewholder and accessing the view from it

                val recycler = pageRecycler ?: return
                val newActiveView = recycler.findViewHolderForAdapterPosition(position) ?: return
                val pageViewHolder = (newActiveView as? VideoPagerAdapter.VideoPageViewHolder) ?: return
                val newContainer = pageViewHolder.binding.videoContentContainer
                activeVideoContainer = newContainer
                activePagerPosition = position
                setVideoPlayerIntoView(newContainer)

                // load video by url
                pageViewHolder.url?.let { url ->
                    setVideoUriToPage(url)
                } ?: run {
                    viewModel.displayVideoNotFoundError()
                }
                restoreStateForCurrentPage()
            }
        }
        pageChangeCallback = callback

        with(binding.videoPager) {
            adapter = pagerAdapter
            registerOnPageChangeCallback(callback)
        }
    }

    private fun savePlaybackState() {
        activePagerPosition?.let {
            val playbackPosition = videoPlayer.currentPosition
            val duration = videoPlayer.duration
            viewModel.saveTimeCode(it, playbackPosition, duration)
        }
    }

    private fun restoreStateForCurrentPage() {
        activePagerPosition?.let {
            val timeCode = viewModel.getLastTimeCode(it)
            videoPlayer.seekTo(timeCode)
        }
    }

    private fun setVideoPlayerIntoView(container: ViewGroup) {
        container.addView(videoSurfaceView)
    }

    private fun cleanView(container: ViewGroup) {
        container.removeAllViews()
    }

    private fun observeViewModelChanges() {
        viewModel.videoPagesInfo.observe(viewLifecycleOwner) {
            val videoUrls = listOf(
                it.firstVideoUrl,
                it.secondVideoUrl,
                it.thirdVideoUrl,
                it.fourthVideoUrl
            )
            pagerAdapter.videoUrlItems = videoUrls
        }
        viewModel.screenGeneralState.observe(viewLifecycleOwner) {
            binding.videoPagerSwipeRefresh.isRefreshing = false
            when(it) {
                is NormalScreenGeneralState -> {
                    binding.loadingErrorMidscreenImage.visibility = View.GONE
                    binding.loadingFrame.visibility = View.GONE
                }
                is LoadingScreenGeneralState -> {
                    binding.loadingErrorMidscreenImage.visibility = View.GONE
                    binding.loadingFrame.visibility = View.VISIBLE
                }
                is ErrorScreenGeneralState -> {
                    binding.loadingFrame.visibility = View.GONE
                    val throwable = it.t
                    context?.let {
                        makeErrorAlert(it, throwable) {
                            viewModel.resetError()
                            binding.loadingErrorMidscreenImage.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun setVideoUriToPage(mediaUrl: String) {
        val uri = Uri.parse(mediaUrl)
        val videoSource = ProgressiveMediaSource.Factory(videoDataSourceFactory)
            .createMediaSource(MediaItem.fromUri(uri))

        videoPlayer.prepare(videoSource)
        videoSurfaceView.player = videoPlayer
    }
}