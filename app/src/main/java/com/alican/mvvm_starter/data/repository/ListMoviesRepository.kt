package com.alican.mvvm_starter.data.repository

import androidx.paging.PagingData
import com.alican.mvvm_starter.data.model.MovieResponseModel
import com.alican.mvvm_starter.data.remote.source.MoviePagingDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListMoviesRepository @Inject constructor(private val dataSource: MoviePagingDataSource) {
    fun getPopularMovie(): Flow<PagingData<MovieResponseModel>> = dataSource.getPopularMovies()

    fun getTopRatedMovie(): Flow<PagingData<MovieResponseModel>> = dataSource.getTopRatedMovies()

    fun getUpComingMovie(): Flow<PagingData<MovieResponseModel>> = dataSource.getUpComingMovies()
    fun getNowPlayingMovies(): Flow<PagingData<MovieResponseModel>> =
        dataSource.getNowPlayingMovies()

    fun getSearchMovies(query: String): Flow<PagingData<MovieResponseModel>> =
        dataSource.getSearchMovies(query)
}