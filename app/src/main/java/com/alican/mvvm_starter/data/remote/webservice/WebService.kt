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

    @GET("discover/movie")
    suspend fun getDiscoverMovies(@Query("page") page : Int): BasePagingResponse<MovieResponseModel>

    @GET("players/id")
    suspend fun getDataRepo(@Query ("id") id: Int): Response<GetDataDto>
}
