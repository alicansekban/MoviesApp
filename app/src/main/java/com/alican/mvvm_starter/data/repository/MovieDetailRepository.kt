package com.alican.mvvm_starter.data.repository

import androidx.datastore.dataStore
import com.alican.mvvm_starter.base.BasePagingResponse
import com.alican.mvvm_starter.data.model.MovieDetailResponse
import com.alican.mvvm_starter.data.model.MovieResponseModel
import com.alican.mvvm_starter.data.remote.source.MovieDetailDataSource
import com.alican.mvvm_starter.domain.model.BaseUIModel
import com.alican.mvvm_starter.domain.model.Error
import com.alican.mvvm_starter.domain.model.Loading
import com.alican.mvvm_starter.domain.model.MovieUIModel
import com.alican.mvvm_starter.domain.model.Success
import com.alican.mvvm_starter.util.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieDetailRepository @Inject constructor(val dataSource: MovieDetailDataSource) {

    suspend fun getMovieDetail(id:Int): ResultWrapper<MovieDetailResponse> =
        dataSource.getMovieDetail(id)
}