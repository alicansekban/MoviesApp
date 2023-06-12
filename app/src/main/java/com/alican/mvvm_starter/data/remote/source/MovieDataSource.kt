package com.alican.mvvm_starter.data.remote.source

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.alican.mvvm_starter.data.model.MovieModel
import com.alican.mvvm_starter.data.model.MovieResponseModel
import com.alican.mvvm_starter.data.remote.paging.MoviePagingSource
import com.alican.mvvm_starter.data.remote.webservice.WebService
import com.alican.mvvm_starter.util.MovieTypeEnum
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieDataSource @Inject constructor(private val webService: WebService) {



    suspend fun getPopularMovies(): Flow<PagingData<MovieResponseModel>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
            jumpThreshold = Int.MIN_VALUE,
            enablePlaceholders = true
        ),
        pagingSourceFactory = {
            MoviePagingSource(webService, MovieTypeEnum.POPULAR_MOVIES)
        }
    ).flow

    suspend fun getTopRatedMovies(): Flow<PagingData<MovieResponseModel>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
            jumpThreshold = Int.MIN_VALUE,
            enablePlaceholders = true
        ),
        pagingSourceFactory = {
            MoviePagingSource(webService, MovieTypeEnum.TOP_RATED_MOVIES)
        }
    ).flow

    suspend fun getUpComingMovies(): Flow<PagingData<MovieResponseModel>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
            jumpThreshold = Int.MIN_VALUE,
            enablePlaceholders = true
        ),
        pagingSourceFactory = {
            MoviePagingSource(webService, MovieTypeEnum.UP_COMING_MOVIES)
        }
    ).flow

    suspend fun getSearchMovies(query: String): Flow<PagingData<MovieResponseModel>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
            jumpThreshold = Int.MIN_VALUE,
            enablePlaceholders = true
        ),
        pagingSourceFactory = {
            MoviePagingSource(webService, MovieTypeEnum.SEARCH_MOVIES, query)
        }
    ).flow
}