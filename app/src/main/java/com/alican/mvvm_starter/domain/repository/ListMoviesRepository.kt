package com.alican.mvvm_starter.domain.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.alican.mvvm_starter.data.model.MovieModel
import com.alican.mvvm_starter.data.remote.source.MovieDataSource
import com.alican.mvvm_starter.domain.mapper.toMovieModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ListMoviesRepository @Inject constructor(private val dataSource: MovieDataSource) {
     suspend fun getPopularMovie(): Flow<PagingData<MovieModel>> = flow {
         dataSource.getPopularMovies().map { pagingData ->
            pagingData.map { it.toMovieModel() }
        }.collect {
            emit(it)
        }
    }

     suspend fun getTopRatedMovie(): Flow<PagingData<MovieModel>> = flow {
        dataSource.getTopRatedMovies().map { pagingData ->
            pagingData.map { it.toMovieModel() }
        }.collect {
            emit(it)
        }
    }

    suspend fun getUpComingMovie(): Flow<PagingData<MovieModel>> = flow {
        dataSource.getUpComingMovies().map { pagingData ->
            pagingData.map { it.toMovieModel() }
        }.collect {
            emit(it)
        }
    }

     suspend fun getSearchMovies(query: String): Flow<PagingData<MovieModel>> = flow {
        dataSource.getSearchMovies(query).map { pagingData ->
            pagingData.map { it.toMovieModel() }
        }.collect {
            emit(it)
        }
    }
}