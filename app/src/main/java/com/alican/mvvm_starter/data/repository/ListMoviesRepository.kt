package com.alican.mvvm_starter.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.alican.mvvm_starter.data.model.MovieModel
import com.alican.mvvm_starter.data.model.MovieResponseModel
import com.alican.mvvm_starter.data.remote.source.MoviePagingDataSource
import com.alican.mvvm_starter.domain.mapper.toMovieModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.lang.IllegalArgumentException
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