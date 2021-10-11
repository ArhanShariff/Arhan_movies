package com.example.assignment.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.assignment.R
import com.example.assignment.data.MoviesPagingSource
import com.example.assignment.data.Result
import com.example.assignment.databinding.FragmentGalleryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery), GalleryPhotoAdapter.OnItemClickListener {

    private val viewModel by viewModels<GalleryViewModel>()

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentGalleryBinding.bind(view)

        val adapter = GalleryPhotoAdapter(this)

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter
        }

        viewModel.response!!.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    override fun onItemClick(movie: Result) {
        val action = GalleryFragmentDirections.actionGalleryFragmentToDetailsFragment(movie)
        findNavController().navigate(action)
    }
}