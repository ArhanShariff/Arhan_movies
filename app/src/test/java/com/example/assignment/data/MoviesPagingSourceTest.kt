package com.example.assignment.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import com.example.assignment.BuildConfig
import com.example.assignment.CoroutineTestRule
import com.example.assignment.api.MovieReviewApi
import com.example.assignment.api.MoviesResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class MoviesPagingSourceTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    @Mock
    lateinit var api: MovieReviewApi

    lateinit var moviesPagingSource: MoviesPagingSource

    companion object {
        val movieResponse =
            MoviesResponse(
                results = listOf(
                    Result(
                        byline = "byline",
                        critics_pick = 6,
                        date_updated = "20/09/2021",
                        headline = "headline",
                        link = Link(
                            suggested_link_text = "suggested_link_text",
                            type = "type",
                            url = "url"
                        ),
                        mpaa_rating = "mpaa_rating",
                        display_title = "display_title",
                        multimedia = Multimedia(
                            height = 150,
                            src = "src",
                            type = "type",
                            width = 250
                        ),
                        opening_date = "opening_date",
                        publication_date = "publication_date",
                        summary_short = "summary_short"
                    ),
                    Result(
                        byline = "byline",
                        critics_pick = 6,
                        date_updated = "20/09/2021",
                        headline = "headline",
                        link = Link(
                            suggested_link_text = "suggested_link_text",
                            type = "type",
                            url = "url"
                        ),
                        mpaa_rating = "mpaa_rating",
                        display_title = "display_title",
                        multimedia = Multimedia(
                            height = 150,
                            src = "src",
                            type = "type",
                            width = 250
                        ),
                        opening_date = "opening_date",
                        publication_date = "publication_date",
                        summary_short = "summary_short"
                    )
                )
            )

    }

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        moviesPagingSource = MoviesPagingSource(api)
    }

    @Test
    fun `movies paging source load - failure - http error`() = runBlockingTest {
        val error = RuntimeException("404", Throwable())
        given(api.getMovies("picks", BuildConfig.API_KEY)).willThrow(error)
        val expectedResult = PagingSource.LoadResult.Error<Int, Result>(error)
        assertEquals(
            expectedResult, moviesPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `movies paging source load - failure - received null`() = runBlockingTest {
        given(api.getMovies("picks", BuildConfig.API_KEY)).willReturn(null)
        val expectedResult = PagingSource.LoadResult.Error<Int, Result>(NullPointerException())
        assertEquals(
            expectedResult.toString(), moviesPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            ).toString()
        )
    }

    @Test
    fun `movies paging source refresh - success`() = runBlockingTest {
        given(api.getMovies("picks", BuildConfig.API_KEY)).willReturn(movieResponse)
        val expectedResult = PagingSource.LoadResult.Page(
            data = movieResponse.results,
            prevKey = null,
            nextKey = null
        )
        assertEquals(
            expectedResult, moviesPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `movies paging source append - success`() = runBlockingTest {
        given(api.getMovies("picks", BuildConfig.API_KEY)).willReturn(movieResponse)
        val expectedResult = PagingSource.LoadResult.Page(
            data = movieResponse.results,
            prevKey = null,
            nextKey = null
        )
        assertEquals(
            expectedResult, moviesPagingSource.load(
                PagingSource.LoadParams.Append(
                    key = 1,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `movies paging source prepend - success`() = runBlockingTest {
        given(api.getMovies("picks", BuildConfig.API_KEY)).willReturn(movieResponse)
        val expectedResult = PagingSource.LoadResult.Page(
            data = movieResponse.results,
            prevKey = -1,
            nextKey = null
        )
        assertEquals(
            expectedResult, moviesPagingSource.load(
                PagingSource.LoadParams.Prepend(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }

}