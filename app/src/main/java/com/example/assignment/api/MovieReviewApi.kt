package com.example.assignment.api

import com.example.assignment.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieReviewApi {

    companion object {
        const val BASE_URL = "https://api.nytimes.com/"
    }

    @GET("svc/movies/v2/reviews/{type}.json")
    suspend fun getMovies(
        @Path("type") type: String = "picks",
        @Query("api-key") key: String = BuildConfig.API_KEY
    ): MoviesResponse

}