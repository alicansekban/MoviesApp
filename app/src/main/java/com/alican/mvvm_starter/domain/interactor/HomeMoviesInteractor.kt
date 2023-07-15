package com.alican.mvvm_starter.domain.interactor

import android.content.Context
import com.alican.mvvm_starter.R
import com.alican.mvvm_starter.data.repository.HomeMoviesRepository
import com.alican.mvvm_starter.domain.mapper.MovieMapper
import com.alican.mvvm_starter.domain.model.BaseUIModel
import com.alican.mvvm_starter.domain.model.Error
import com.alican.mvvm_starter.domain.model.Loading
import com.alican.mvvm_starter.domain.model.MovieUIModel
import com.alican.mvvm_starter.domain.model.Success
import com.alican.mvvm_starter.util.ResultWrapper
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeMoviesInteractor @Inject constructor(
    val repository: HomeMoviesRepository,
    val mapper: MovieMapper,
    @ApplicationContext private val context : Context
) {

    fun getUpcomingMovies(): Flow<BaseUIModel<List<MovieUIModel>>> {
        return flow {
            emit(Loading())
            emit(
                when (val result = repository.getUpComingMovies()) {
                    is ResultWrapper.GenericError -> Error(context.getString(R.string.error_message))
                    ResultWrapper.Loading -> Loading()
                    ResultWrapper.NetworkError -> Error(context.getString(R.string.connection_error_msg))
                    is ResultWrapper.Success -> Success(result.value.results.take(7).map {
                       mapper.mapOnMovieResponse(it)
                    })
                }
            )
        }
    }

    fun getNowPlayingMovies(): Flow<BaseUIModel<List<MovieUIModel>>> {
        return flow {
            emit(Loading())
            emit(
                when (val result = repository.getNowPlayingMovies()) {
                    is ResultWrapper.GenericError -> Error(context.getString(R.string.error_message))
                    ResultWrapper.Loading -> Loading()
                    ResultWrapper.NetworkError -> Error(context.getString(R.string.connection_error_msg))
                    is ResultWrapper.Success -> Success(result.value.results.take(7).map {
                        mapper.mapOnMovieResponse(it)
                    })
                }
            )
        }
    }

    fun getTopRatedMovies(): Flow<BaseUIModel<List<MovieUIModel>>> {
        return flow {
            emit(Loading())
            emit(
                when (val result = repository.getTopRatedMovies()) {
                    is ResultWrapper.GenericError -> Error(context.getString(R.string.error_message))
                    ResultWrapper.Loading -> Loading()
                    ResultWrapper.NetworkError -> Error(context.getString(R.string.connection_error_msg))
                    is ResultWrapper.Success -> Success(result.value.results.take(7).map {
                        mapper.mapOnMovieResponse(it)
                    })
                }
            )
        }
    }

    fun getPopularMovies(): Flow<BaseUIModel<List<MovieUIModel>>> {
        return flow {
            emit(Loading())
            emit(
                when (val result = repository.getPopularMovies()) {
                    is ResultWrapper.GenericError -> Error(context.getString(R.string.error_message))
                    ResultWrapper.Loading -> Loading()
                    ResultWrapper.NetworkError -> Error(context.getString(R.string.connection_error_msg))
                    is ResultWrapper.Success -> Success(result.value.results.take(7).map {
                        mapper.mapOnMovieResponse(it)
                    })
                }
            )
        }
    }
}