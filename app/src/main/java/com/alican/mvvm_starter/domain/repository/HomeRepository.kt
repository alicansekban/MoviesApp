package com.alican.mvvm_starter.domain.repository

import com.alican.mvvm_starter.data.model.MovieModel
import com.alican.mvvm_starter.data.model.MovieResponseModel
import com.alican.mvvm_starter.data.remote.webservice.WebService
import com.alican.mvvm_starter.domain.mapper.toMovieEntity
import com.alican.mvvm_starter.domain.mapper.toMovieModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepository @Inject constructor(private val webService: WebService) {

    private val _dataList = MutableStateFlow<List<MovieModel>>(emptyList())
    val dataList: StateFlow<List<MovieModel>> get() = _dataList

    suspend fun getDiscoverMovies(): Flow<List<MovieModel>> {
        val response = webService.getDiscoverMovies(1)
        val movieModels = response.results.map {
            it.toMovieModel()
        }
        return flow {
            emit(movieModels)
        }

    }

    suspend fun getUpComingMovies(): Flow<List<MovieResponseModel>> {
        val response = webService.getUpComingMovies(1)
        val movieModels = response.results.map { it.toMovieModel() }
        return flow {
               emit(response.results)
        }

    }

    suspend fun getPopularMovies(): Flow<List<MovieModel>> {
        val response = webService.getPopularMovies(1)
        val movieModels = response.results.map {
            it.toMovieModel()
        }
        return flow {
            emit(movieModels)
        }

    }

    suspend fun getPopularMoviesStateFlow() {
        try {
            val response = webService.getDiscoverMovies(1)
            val movieModels = response.results.map {
                it.toMovieModel()
            }
            _dataList.emit(movieModels)
        } catch (e: Exception) {
            // Hata durumunda gerekli işlemleri burada yapabilirsiniz
        }
    }


}