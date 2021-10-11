package com.example.assignment.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.assignment.data.MoviesRepository
import com.example.assignment.data.Result
import kotlinx.coroutines.launch

class GalleryViewModel @ViewModelInject constructor(
    private val repository: MoviesRepository
) : ViewModel() {
    var response: LiveData<PagingData<Result>>? = null
    init {
        viewModelScope.launch {
            response = repository.getMovies().cachedIn(viewModelScope)
        }
    }

}