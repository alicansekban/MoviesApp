package com.alican.mvvm_starter.domain.interactor

import android.content.Context
import com.alican.mvvm_starter.R
import com.alican.mvvm_starter.data.repository.MovieDetailRepository
import com.alican.mvvm_starter.domain.mapper.MovieMapper
import com.alican.mvvm_starter.domain.model.BaseUIModel
import com.alican.mvvm_starter.domain.model.Error
import com.alican.mvvm_starter.domain.model.Loading
import com.alican.mvvm_starter.domain.model.MovieCreditsUIModel
import com.alican.mvvm_starter.domain.model.MovieDetailUIModel
import com.alican.mvvm_starter.domain.model.Success
import com.alican.mvvm_starter.util.ResultWrapper
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieDetailInteractor @Inject constructor(
    val repository: MovieDetailRepository,
    val mapper: MovieMapper,
    @ApplicationContext private val context : Context
) {

    fun getMovieDetail(id: Int): Flow<BaseUIModel<MovieDetailUIModel>> {
        return flow {
            emit(Loading())
            emit(
                when (val result = repository.getMovieDetail(id)) {
                    is ResultWrapper.GenericError -> Error(context.getString(R.string.error_message))
                    ResultWrapper.Loading -> Loading()
                    ResultWrapper.NetworkError -> Error(context.getString(R.string.connection_error_msg))
                    is ResultWrapper.Success -> Success(
                        mapper.mapMovieDetailResponseToMovieDetailUIModel(
                            result.value
                        )
                    )
                }
            )
        }
    }

    fun getMovieCredits(id: Int): Flow<BaseUIModel<MovieCreditsUIModel>> {
        return flow {
            emit(Loading())
            emit(
                when (val result = repository.getMovieCredits(id)) {
                    is ResultWrapper.GenericError -> Error(context.getString(R.string.error_message))
                    ResultWrapper.Loading -> Loading()
                    ResultWrapper.NetworkError -> Error(context.getString(R.string.connection_error_msg))
                    is ResultWrapper.Success -> Success(
                        mapper.mapMovieCreditResponseToMovieCreditsUIModel(
                            result.value
                        )
                    )
                }
            )
        }
    }
}