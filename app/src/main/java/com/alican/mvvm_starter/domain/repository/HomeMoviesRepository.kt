package com.alican.mvvm_starter.domain.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.alican.mvvm_starter.data.local.AppDatabase
import com.alican.mvvm_starter.data.local.model.MovieEntity
import com.alican.mvvm_starter.data.model.MovieModel
import com.alican.mvvm_starter.data.remote.mediator.HomeMoviesMediator
import com.alican.mvvm_starter.data.remote.source.HomeDataSource
import com.alican.mvvm_starter.data.remote.webservice.WebService
import com.alican.mvvm_starter.domain.mapper.toMovieModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.IllegalArgumentException
import javax.inject.Inject

class HomeMoviesRepository @Inject constructor(
    private val webService: WebService,
    private val database: AppDatabase,
    private val homeDataSource: HomeDataSource
) {
    @OptIn(ExperimentalPagingApi::class)
    fun discoverMovie(): Flow<PagingData<MovieEntity>> {
        val dbSource = {
            database.homeMoviesDao().getPagingMovie()
        }
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            remoteMediator = HomeMoviesMediator(
                database, webService
            ),
            pagingSourceFactory = dbSource
        )
            .flow
    }

    suspend fun getUpComingMovies(): Flow<List<MovieModel>> = flow {
        val response = homeDataSource.getUpComingMovies()
        val movieModels = response.take(7).map {
            it.toMovieModel()
        }

        emit(movieModels)

    }.catch { e ->
        if (e is IllegalArgumentException ) throw e else emit(emptyList())
    }.flowOn(Dispatchers.IO)

    suspend fun getPopularMovies(): Flow<List<MovieModel>> = flow {
        val response = homeDataSource.getPopularMovies()
        val movieModels = response.take(7).map {
            it.toMovieModel()
        }

        emit(movieModels)


    }.catch { e ->
        if (e is IllegalArgumentException ) throw e else emit(emptyList())
    }.flowOn(Dispatchers.IO)

    suspend fun getTopRatedMovies(): Flow<List<MovieModel>> = flow {
        val response = homeDataSource.getTopRatedMovies()
        val movieModels = response.take(7).map {
            it.toMovieModel()
        }
        emit(movieModels)
    }.catch { e ->
        if (e is IllegalArgumentException ) throw e else emit(emptyList())
    }.flowOn(Dispatchers.IO)

    suspend fun getNowPlayingMovies(): Flow<List<MovieModel>> = flow {
        val response = homeDataSource.getNowPlayingMovies()
        val movieModels = response.take(7).map {
            it.toMovieModel()
        }
        emit(movieModels)
    }.catch { e ->
        if (e is IllegalArgumentException ) throw e else emit(emptyList())
    }.flowOn(Dispatchers.IO)
}