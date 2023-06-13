package com.alican.mvvm_starter.data.remote.source

import com.alican.mvvm_starter.data.model.MovieResponseModel
import com.alican.mvvm_starter.data.remote.webservice.WebService
import com.alican.mvvm_starter.util.ResultWrapper
import com.alican.mvvm_starter.util.safeApiCall
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class HomeDataSource @Inject constructor(private val webService: WebService) {

    suspend fun getPopularMovies() : List<MovieResponseModel> {
        val list = mutableListOf<MovieResponseModel>()
        when (val response = safeApiCall(Dispatchers.IO) { webService.getPopularMovies(1) }) {
            is ResultWrapper.Success -> {
                list.addAll(response.value.results)
            }
            is ResultWrapper.GenericError -> {

            }
            else -> {}
        }
        return list
    }
    suspend fun getUpComingMovies() : List<MovieResponseModel> {
        val list = mutableListOf<MovieResponseModel>()
        when (val response = safeApiCall(Dispatchers.IO) { webService.getUpComingMovies(1) }) {
            is ResultWrapper.Success -> {
                list.addAll(response.value.results)
            }
            is ResultWrapper.GenericError -> {

            }
            else -> {}
        }
        return list
    }
    suspend fun getTopRatedMovies() : List<MovieResponseModel> {
        val list = mutableListOf<MovieResponseModel>()
        when (val response = safeApiCall(Dispatchers.IO) { webService.getTopRatedApi(1) }) {
            is ResultWrapper.Success -> {
                list.addAll(response.value.results)
            }
            is ResultWrapper.GenericError -> {

            }
            else -> {}
        }
        return list
    }
    suspend fun getNowPlayingMovies() : List<MovieResponseModel> {
        val list = mutableListOf<MovieResponseModel>()
        when (val response = safeApiCall(Dispatchers.IO) { webService.getNowPlayingMovies(1) }) {
            is ResultWrapper.Success -> {
                list.addAll(response.value.results)
            }
            is ResultWrapper.GenericError -> {

            }
            else -> {}
        }
        return list
    }
}