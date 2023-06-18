package com.alican.mvvm_starter.data.repository

import com.alican.mvvm_starter.data.model.MovieCreditResponse
import com.alican.mvvm_starter.data.model.MovieDetailResponse
import com.alican.mvvm_starter.data.remote.source.MovieDetailDataSource
import com.alican.mvvm_starter.util.ResultWrapper
import javax.inject.Inject

class MovieDetailRepository @Inject constructor(val dataSource: MovieDetailDataSource) {

    suspend fun getMovieDetail(id:Int): ResultWrapper<MovieDetailResponse> =
        dataSource.getMovieDetail(id)

    suspend fun getMovieCredits(id:Int): ResultWrapper<MovieCreditResponse> =
        dataSource.getMovieCredits(id)
}