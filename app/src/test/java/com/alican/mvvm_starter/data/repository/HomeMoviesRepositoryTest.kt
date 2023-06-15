package com.alican.mvvm_starter.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alican.mvvm_starter.base.BasePagingResponse
import com.alican.mvvm_starter.data.local.AppDatabase
import com.alican.mvvm_starter.data.model.MovieResponseModel
import com.alican.mvvm_starter.data.remote.api.WebService
import com.alican.mvvm_starter.data.remote.source.HomeDataSource
import com.alican.mvvm_starter.util.ResultWrapper
import io.mockk.clearAllMocks
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class HomeMoviesRepositoryTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var webService: WebService

    @Mock
    private lateinit var database: AppDatabase

    @Mock
    private lateinit var homeDataSource: HomeDataSource

    private lateinit var homeMoviesRepository: HomeMoviesRepository

    @Before
    fun setup() {
        clearAllMocks()
        MockitoAnnotations.openMocks(this)
        homeMoviesRepository = HomeMoviesRepository(webService, database, homeDataSource)
    }


    @Test
    fun testGetUpComingMovies() = runBlocking {
        // Hazırlık
        val expectedResponse = ResultWrapper.Success(
            BasePagingResponse(
                page = 1,
                results = listOf<MovieResponseModel>(),
                total_pages = 10,
                total_results = 100
            )
        )
        `when`(homeDataSource.getUpComingMovies()).thenReturn(expectedResponse)

        // Test
        val actualResponse = homeMoviesRepository.getUpComingMovies()

        // Doğrulama
        assertEquals(expectedResponse, actualResponse)
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
        `when`(homeDataSource.getPopularMovies()).thenReturn(expectedResponse)

        // Test
        val actualResponse = homeMoviesRepository.getPopularMovies()

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
        `when`(homeDataSource.getTopRatedMovies()).thenReturn(expectedResponse)

        // Test
        val actualResponse = homeMoviesRepository.getTopRatedMovies()

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
        `when`(homeDataSource.getNowPlayingMovies()).thenReturn(expectedResponse)

        // Test
        val actualResponse = homeMoviesRepository.getNowPlayingMovies()

        // Doğrulama
        assertEquals(expectedResponse, actualResponse)
    }
}
