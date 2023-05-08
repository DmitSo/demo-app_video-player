package com.linkedin.dzmitrysalauyeu.demoappvideoplayer.videoscreen.presentation.pagescontainer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.databinding.ItemVideoPageBinding

class VideoPagerAdapter : ListAdapter<String, VideoPagerAdapter.VideoPageViewHolder>(
    object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem === newItem
        override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem
    }
) {

    var videoUrlItems = listOf<String>()
        set(value) {
            field = value
            submitList(field)
        }

    override fun getItemCount() = videoUrlItems.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoPageViewHolder {
        val binding = ItemVideoPageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return VideoPageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoPageViewHolder, position: Int) {
        holder.binding.root.tag = holder
        holder.url = videoUrlItems.getOrNull(position)
    }

    inner class VideoPageViewHolder(
        val binding: ItemVideoPageBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        var url: String? = null
    }
}