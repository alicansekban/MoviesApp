package com.alican.mvvm_starter.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.alican.mvvm_starter.base.BasePagingResponse
import com.alican.mvvm_starter.data.local.AppDatabase
import com.alican.mvvm_starter.data.local.model.MovieEntity
import com.alican.mvvm_starter.data.model.MovieModel
import com.alican.mvvm_starter.data.model.MovieResponseModel
import com.alican.mvvm_starter.data.remote.mediator.HomeMoviesMediator
import com.alican.mvvm_starter.data.remote.source.HomeDataSource
import com.alican.mvvm_starter.data.remote.webservice.WebService
import com.alican.mvvm_starter.domain.mapper.toMovieModel
import com.alican.mvvm_starter.util.ResultWrapper
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

    suspend fun getUpComingMovies(): ResultWrapper<BasePagingResponse<MovieResponseModel>> =
        homeDataSource.getUpComingMovies()

    suspend fun getPopularMovies(): ResultWrapper<BasePagingResponse<MovieResponseModel>> =
        homeDataSource.getPopularMovies()

    suspend fun getTopRatedMovies(): ResultWrapper<BasePagingResponse<MovieResponseModel>> =
        homeDataSource.getTopRatedMovies()

    suspend fun getNowPlayingMovies(): ResultWrapper<BasePagingResponse<MovieResponseModel>> =
        homeDataSource.getNowPlayingMovies()


}