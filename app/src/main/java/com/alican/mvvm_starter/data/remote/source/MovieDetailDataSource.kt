package com.alican.mvvm_starter.data.remote.source

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.alican.mvvm_starter.data.model.MovieCreditResponse
import com.alican.mvvm_starter.data.model.MovieDetailResponse
import com.alican.mvvm_starter.data.model.MovieResponseModel
import com.alican.mvvm_starter.data.model.MovieReviewResponse
import com.alican.mvvm_starter.data.remote.api.WebService
import com.alican.mvvm_starter.data.remote.paging.MovieDetailReviewsSource
import com.alican.mvvm_starter.data.remote.paging.MoviePagingSource
import com.alican.mvvm_starter.util.MovieTypeEnum
import com.alican.mvvm_starter.util.ResultWrapper
import com.alican.mvvm_starter.util.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieDetailDataSource @Inject constructor(private val webService: WebService) {


    fun getMovieReviews(id:Int): Flow<PagingData<MovieReviewResponse>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
            jumpThreshold = Int.MIN_VALUE,
            enablePlaceholders = true
        ),
        pagingSourceFactory = {
            MovieDetailReviewsSource(webService,id)
        }
    ).flow
    suspend fun getMovieDetail(id:Int):ResultWrapper<MovieDetailResponse> {
        return safeApiCall(Dispatchers.IO) { webService.getMovieDetail(id) }
    }

    suspend fun getMovieCredits(id:Int):ResultWrapper<MovieCreditResponse> {
        return safeApiCall(Dispatchers.IO) { webService.getMovieCredits(id) }
    }
}