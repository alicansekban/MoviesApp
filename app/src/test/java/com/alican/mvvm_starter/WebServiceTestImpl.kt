package com.alican.mvvm_starter

import com.alican.mvvm_starter.base.BasePagingResponse
import com.alican.mvvm_starter.data.model.MovieCreditResponse
import com.alican.mvvm_starter.data.model.MovieDetailResponse
import com.alican.mvvm_starter.data.model.MovieResponseModel
import com.alican.mvvm_starter.data.model.MovieReviewResponse
import com.alican.mvvm_starter.data.remote.api.WebService

class WebServiceTestImpl : WebService {
    override suspend fun getUpComingMovies(page: Int): BasePagingResponse<MovieResponseModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getPopularMovies(page: Int): BasePagingResponse<MovieResponseModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getTopRatedApi(page: Int): BasePagingResponse<MovieResponseModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getNowPlayingMovies(page: Int): BasePagingResponse<MovieResponseModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getSearchMovieApi(
        page: Int,
        query: String
    ): BasePagingResponse<MovieResponseModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieDetail(id: Int): MovieDetailResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieReviews(
        id: Int,
        page: Int
    ): BasePagingResponse<MovieReviewResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieCredits(id: Int): MovieCreditResponse {
        TODO("Not yet implemented")
    }
}