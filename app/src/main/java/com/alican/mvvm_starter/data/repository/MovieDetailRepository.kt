package com.alican.mvvm_starter.data.repository

import com.alican.mvvm_starter.data.model.MovieDetailResponse
import com.alican.mvvm_starter.data.remote.source.MovieDetailDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieDetailRepository @Inject constructor(dataSource: MovieDetailDataSource) {

    suspend fun getMovieDetail(id:Int) : Flow<MovieDetailResponse> = flow {
        
    }
}