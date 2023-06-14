package com.alican.mvvm_starter.data.remote.api

import com.alican.mvvm_starter.base.BasePagingResponse
import com.alican.mvvm_starter.base.BaseResponse
import com.alican.mvvm_starter.data.model.MovieCreditResponse
import com.alican.mvvm_starter.data.model.MovieDetailResponse
import com.alican.mvvm_starter.data.model.MovieResponseModel
import com.alican.mvvm_starter.data.model.MovieReviewResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WebService {

    // DISCOVER
    @GET("discover/movie")
    suspend fun getDiscoverMovies(@Query("page") page : Int): BasePagingResponse<MovieResponseModel>

    @GET("discover/tv")
    suspend fun getDiscoverTvShows(@Query("page") page : Int): BasePagingResponse<MovieResponseModel>


    // MOVIE LISTS
    @GET("movie/upcoming")
    suspend fun getUpComingMovies(@Query("page") page : Int): BasePagingResponse<MovieResponseModel>

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page : Int): BasePagingResponse<MovieResponseModel>
    @GET("movie/top_rated")
    suspend fun getTopRatedApi(@Query("page") page: Int): BasePagingResponse<MovieResponseModel>
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("page") page: Int): BasePagingResponse<MovieResponseModel>
    @GET("movie/latest")
    suspend fun getLatestMovies(@Query("page") page: Int): BasePagingResponse<MovieResponseModel>
    @GET("search/movie")
    suspend fun getSearchMovieApi(
        @Query("page") page: Int,
        @Query("query") query: String
    ): BasePagingResponse<MovieResponseModel>


    @GET("movie/{id}")
    suspend fun getMovieDetail(@Path("id") id : Int) : MovieDetailResponse

    @GET("movie/{id}/similar")
    suspend fun getSimilarMovies(@Path("id") id : Int) : BasePagingResponse<MovieResponseModel>

    @GET("movie/{id}/reviews")
    suspend fun getMovieReviews(@Path("id") id : Int,@Query("page") page: Int) : BasePagingResponse<MovieReviewResponse>

    @GET("movie/{id}/credits")
    suspend fun getMovieCredits(@Path("id") id : Int) : MovieCreditResponse

}
