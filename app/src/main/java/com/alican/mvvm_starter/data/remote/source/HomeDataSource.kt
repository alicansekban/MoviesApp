package com.alican.mvvm_starter.data.remote.source

import com.alican.mvvm_starter.base.BasePagingResponse
import com.alican.mvvm_starter.data.model.MovieResponseModel
import com.alican.mvvm_starter.data.remote.api.WebService
import com.alican.mvvm_starter.util.ResultWrapper
import com.alican.mvvm_starter.util.safeApiCall
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class HomeDataSource @Inject constructor(private val webService: WebService) {

    suspend fun getPopularMovies():ResultWrapper<BasePagingResponse<MovieResponseModel>> {
        return safeApiCall(Dispatchers.IO) { webService.getPopularMovies(1) }
    }

    suspend fun getUpComingMovies(): ResultWrapper<BasePagingResponse<MovieResponseModel>> {
        return safeApiCall(Dispatchers.IO) { webService.getUpComingMovies(1) }
    }

    suspend fun getTopRatedMovies(): ResultWrapper<BasePagingResponse<MovieResponseModel>> {
        return safeApiCall(Dispatchers.IO) { webService.getTopRatedApi(1) }
    }

        suspend fun getNowPlayingMovies(): ResultWrapper<BasePagingResponse<MovieResponseModel>> {
            return safeApiCall(Dispatchers.IO) { webService.getNowPlayingMovies(1) }
        }
}