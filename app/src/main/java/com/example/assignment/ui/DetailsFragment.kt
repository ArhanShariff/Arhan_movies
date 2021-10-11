package com.example.assignment.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.assignment.R
import com.example.assignment.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment(R.layout.fragment_details) {

   private val args by navArgs<DetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentDetailsBinding.bind(view)

        binding.apply {
            val movie = args.movie

            Glide.with(this@DetailsFragment)
                .load(movie.multimedia.src)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        title.isVisible = true
                        byline.isVisible = true
                        headline.isVisible = true
                        summary.isVisible = true
                        return false
                    }
                })
                .into(imageView)

            title.text = movie.display_title
            byline.text = "Byline: ${movie.byline}"
            headline.text = "Headline: ${movie.headline}"
            summary.text = "Summary: ${movie.summary_short}"

        }
    }


}