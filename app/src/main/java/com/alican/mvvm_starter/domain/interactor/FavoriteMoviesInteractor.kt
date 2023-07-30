package com.alican.mvvm_starter.domain.interactor

import android.content.Context
import com.alican.mvvm_starter.R
import com.alican.mvvm_starter.data.repository.FavoriteMoviesRepository
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

class FavoriteMoviesInteractor @Inject constructor(
    private val repository: FavoriteMoviesRepository,
    private val mapper: MovieMapper,
    @ApplicationContext private val context: Context
) {

    suspend fun insertFavoriteMovie(movie: MovieUIModel): Flow<BaseUIModel<Boolean>> {
        return flow {
            emit(Loading())
            emit(
                when (val result =
                    repository.insertFavoriteMovie(mapper.mapMovietoFavoriteEntity(movie))) {
                    is ResultWrapper.GenericError -> Error(context.getString(R.string.error_message))
                    ResultWrapper.Loading -> Loading()
                    ResultWrapper.NetworkError -> Error(context.getString(R.string.connection_error_msg))
                    is ResultWrapper.Success -> Success(result.value)
                }

            )
        }
    }
}