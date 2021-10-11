package com.example.assignment.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.assignment.api.MovieReviewApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(private val movieReviewApi: MovieReviewApi) {

     fun getMovies() =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviesPagingSource(movieReviewApi) }
        ).liveData

}