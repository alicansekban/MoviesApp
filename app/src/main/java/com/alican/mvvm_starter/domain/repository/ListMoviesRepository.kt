package com.alican.mvvm_starter.domain.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.alican.mvvm_starter.data.model.MovieModel
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
    suspend fun getPopularMovie(): Flow<PagingData<MovieModel>> = flow {
        dataSource.getPopularMovies().map { pagingData ->
            pagingData.map { it.toMovieModel() }
        }.collect {
            emit(it)
        }
    }.catch { e ->
        if (e is IllegalArgumentException) throw e else emit(PagingData.empty())
    }

    suspend fun getTopRatedMovie(): Flow<PagingData<MovieModel>> = flow {
        dataSource.getTopRatedMovies().map { pagingData ->
            pagingData.map { it.toMovieModel() }
        }.collect {
            emit(it)
        }
    }.catch { e ->
        if (e is IllegalArgumentException) throw e else emit(PagingData.empty())
    }

    suspend fun getUpComingMovie(): Flow<PagingData<MovieModel>> = flow {
        dataSource.getUpComingMovies().map { pagingData ->
            pagingData.map { it.toMovieModel() }
        }.collect {
            emit(it)
        }
    }.catch { e ->
        if (e is IllegalArgumentException) throw e else emit(PagingData.empty())
    }

    suspend fun getNowPlayingMovies(): Flow<PagingData<MovieModel>> = flow {
        dataSource.getNowPlayingMovies().map { pagingData ->
            pagingData.map { it.toMovieModel() }
        }.collect {
            emit(it)
        }
    }.catch { e ->
        if (e is IllegalArgumentException) throw e else emit(PagingData.empty())
    }


    suspend fun getLatestMovies(): Flow<PagingData<MovieModel>> = flow {
        dataSource.getLatestMovies().map { pagingData ->
            pagingData.map { it.toMovieModel() }
        }.collect {
            emit(it)
        }
    }.catch { e ->
        if (e is IllegalArgumentException) throw e else emit(PagingData.empty())
    }

    suspend fun getSearchMovies(query: String): Flow<PagingData<MovieModel>> = flow {
        dataSource.getSearchMovies(query).map { pagingData ->
            pagingData.map { it.toMovieModel() }
        }.collect {
            emit(it)
        }
    }.catch { e ->
        if (e is IllegalArgumentException) throw e else emit(PagingData.empty())
    }
}