package com.alican.mvvm_starter.data.remote.source

import com.alican.mvvm_starter.data.model.MovieDetailResponse
import com.alican.mvvm_starter.data.remote.api.WebService
import com.alican.mvvm_starter.util.ResultWrapper
import com.alican.mvvm_starter.util.safeApiCall
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class MovieDetailDataSource @Inject constructor(private val webService: WebService) {

    suspend fun getMovieDetail(id:Int):ResultWrapper<MovieDetailResponse> {
        return safeApiCall(Dispatchers.IO) { webService.getMovieDetail(id) }
    }
}