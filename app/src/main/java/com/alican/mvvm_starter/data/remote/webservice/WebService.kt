package com.alican.mvvm_starter.data.remote.webservice

import com.alican.mvvm_starter.base.BasePagingResponse
import com.alican.mvvm_starter.data.remote.dto.GetDataDto
import com.alican.mvvm_starter.base.BaseResponse
import com.alican.mvvm_starter.data.model.MovieResponseModel
import com.alican.mvvm_starter.data.model.NowPlayingMoviesModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

    // DISCOVER
    @GET("discover/movie")
    suspend fun getDiscoverMovies(@Query("page") page : Int): BasePagingResponse<MovieResponseModel>

    @GET("discover/tv")
    suspend fun getDiscoverTvShows(@Query("page") page : Int): BasePagingResponse<MovieResponseModel>

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

}
