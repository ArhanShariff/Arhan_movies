package com.example.assignment.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.assignment.data.Movies
import com.example.assignment.data.Result
import com.example.assignment.databinding.ItemPhotoBinding

class GalleryPhotoAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<Result, GalleryPhotoAdapter.PhotoViewHolder>(PHOTO_COMPARATOR){
    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding =
            ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return PhotoViewHolder(binding)
    }

    inner class PhotoViewHolder(private val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position =bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onItemClick(item)
                    }
                }
            }
        }

        fun bind(photo: Result) {
            binding.apply {
                Glide.with(itemView)
                    .load(photo.multimedia.src)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView)

                textViewUserName.text = photo.display_title
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(movie: Result)
    }

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result) =
                oldItem.display_title == newItem.display_title

            override fun areContentsTheSame(oldItem: Result, newItem: Result) =
                oldItem == newItem
        }
    }
}