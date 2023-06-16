package com.alican.mvvm_starter.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.alican.mvvm_starter.base.BasePagingResponse
import com.alican.mvvm_starter.data.local.AppDatabase
import com.alican.mvvm_starter.data.local.model.ReviewsEntity
import com.alican.mvvm_starter.data.model.MovieResponseModel
import com.alican.mvvm_starter.data.remote.api.WebService
import com.alican.mvvm_starter.data.remote.mediator.HomeMoviesMediator
import com.alican.mvvm_starter.data.remote.source.HomeDataSource
import com.alican.mvvm_starter.domain.mapper.MovieMapper
import com.alican.mvvm_starter.util.ResultWrapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeMoviesRepository @Inject constructor(
    private val webService: WebService,
    private val database: AppDatabase,
    private val homeDataSource: HomeDataSource,
    private val mapper: MovieMapper
) {
    @OptIn(ExperimentalPagingApi::class)
    fun discoverMovie(id: Int): Flow<PagingData<ReviewsEntity>> {
        val dbSource = {
            database.reviewsDao().getPagingReviews()
        }
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            remoteMediator = HomeMoviesMediator(
                database, webService, mapper, id
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