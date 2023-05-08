package com.linkedin.dzmitrysalauyeu.demoappvideoplayer.videoscreen.presentation.pagescontainer

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
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
import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.R
import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.databinding.FragmentVideoPagesContainerBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class VideoPagesContainerFragment : Fragment() {

    /**
     * Lateinits.. idk, if you can use it, you can use it
     */

    private val viewModel: VideoPagesContainerViewModel by viewModels()
    private lateinit var binding: FragmentVideoPagesContainerBinding

    private var activeVideoContainer: ViewGroup? = null

    private val pagerAdapter = VideoPagerAdapter()
    private lateinit var videoSurfaceView: PlayerView
    private lateinit var videoPlayer: SimpleExoPlayer

    private lateinit var videoDataSourceFactory: DefaultDataSourceFactory

    /**
     * Self-roast: Data binding functionality haven't been used. Better to use ViewBinding or Compose
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_video_pages_container,
            container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModelChanges()
        initVideoPlayer()
        setupVideoPager()

        viewModel.requestVideoPagesInfo()
    }

    /**
     * Self-roast: should handle reinitializing at onResume, moving impl from onViewCreated!
     * Also should save playback state of the active item and its position
     * for screen rotation (VM, onSaveInstanceState, etc)
     * Like that:
     */
//    override fun onResume() {
//        super.onResume()
//        initVideoPlayer()
//        setupVideoPager()
//        mySuperMethodToSaveState()
//    }

    /**
     * Self-roast: following should be at onStop instead of onDestroy: when going to background, playback are active
     */
    override fun onDestroy() {
        super.onDestroy()

        // release player
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
        with(binding.videoPager) {
            adapter = pagerAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    activeVideoContainer?.let { cleanView(it) }
                    activeVideoContainer = null

                    // get corresponding view to put video view in
                    // by getting viewholder and accessing the view from it

                    /**
                     * Self-roast: Styling should be more linear, avoid "!!",
                     * add error processing right here or in VM
                     */

                    // View == RecyclerView of viewpager
                    (getChildAt(0) as? RecyclerView)
                        // View == the item itself
                        ?.findViewHolderForAdapterPosition(position) ?.let {
                            // get viewholder stored in view as a tag
                            (it as? VideoPagerAdapter.VideoPageViewHolder)?.let {
                                activeVideoContainer = it.binding.videoContentContainer
                                setVideoPlayerIntoView(activeVideoContainer!!)

                                // load video by url
                                if (it.url != null) {
                                    setVideoUriToPage(it.url!!)
                                } else {
                                    // TODO
                                }
                            }
                        }
                }
            })
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
    }

    private fun setVideoUriToPage(mediaUrl: String) {
        val uri = Uri.parse(mediaUrl)
        val videoSource = ProgressiveMediaSource.Factory(videoDataSourceFactory)
            .createMediaSource(MediaItem.fromUri(uri))

        videoPlayer.prepare(videoSource)
        videoSurfaceView.player = videoPlayer
    }
}