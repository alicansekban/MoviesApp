package com.alican.mvvm_starter.data.datasource

import com.alican.mvvm_starter.base.BasePagingResponse
import com.alican.mvvm_starter.data.model.MovieResponseModel
import com.alican.mvvm_starter.data.remote.api.WebService
import com.alican.mvvm_starter.data.remote.source.HomeDataSource
import com.alican.mvvm_starter.util.ResultWrapper
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class HomeDataSourceTest {

    @Mock
    private lateinit var webService: WebService

    private lateinit var homeDataSource: HomeDataSource

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        homeDataSource = HomeDataSource(webService)
    }

    @Test
    fun testGetPopularMovies() = runBlocking {
        // Hazırlık
        val expectedResponse = ResultWrapper.Success(
            BasePagingResponse(
                page = 1,
                results = listOf<MovieResponseModel>(),
                total_pages = 10,
                total_results = 100
            )
        )
        `when`(webService.getPopularMovies(1)).thenReturn(expectedResponse.value)

        // Test
        val actualResponse = homeDataSource.getPopularMovies()

        // Doğrulama
        assertEquals(expectedResponse, actualResponse)
    }

    @Test
    fun testGetUpcomingMovies() = runBlocking {
        // Hazırlık
        val expectedResponse = ResultWrapper.Success(
            BasePagingResponse(
                page = 1,
                results = listOf<MovieResponseModel>(),
                total_pages = 10,
                total_results = 100
            )
        )
        `when`(webService.getUpComingMovies(1)).thenReturn(expectedResponse.value)

        // Test
        val actualResponse = homeDataSource.getUpComingMovies()

        // Doğrulama
        assertEquals(expectedResponse, actualResponse)
    }

    @Test
    fun testGetTopRatedMovies() = runBlocking {
        // Hazırlık
        val expectedResponse = ResultWrapper.Success(
            BasePagingResponse(
                page = 1,
                results = listOf<MovieResponseModel>(),
                total_pages = 10,
                total_results = 100
            )
        )
        `when`(webService.getTopRatedApi(1)).thenReturn(expectedResponse.value)

        // Test
        val actualResponse = homeDataSource.getTopRatedMovies()

        // Doğrulama
        assertEquals(expectedResponse, actualResponse)
    }

    @Test
    fun testGetNowPlayingMovies() = runBlocking {
        // Hazırlık
        val expectedResponse = ResultWrapper.Success(
            BasePagingResponse(
                page = 1,
                results = listOf<MovieResponseModel>(),
                total_pages = 10,
                total_results = 100
            )
        )
        `when`(webService.getNowPlayingMovies(1)).thenReturn(expectedResponse.value)

        // Test
        val actualResponse = homeDataSource.getNowPlayingMovies()

        // Doğrulama
        assertEquals(expectedResponse, actualResponse)
    }
}


