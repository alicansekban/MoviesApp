package com.alican.mvvm_starter.domain.usecase

import com.alican.mvvm_starter.data.local.model.FavoritesEntity
import com.alican.mvvm_starter.data.repository.FavoriteMoviesRepository
import com.alican.mvvm_starter.domain.model.BaseUIModel
import com.alican.mvvm_starter.domain.model.Error
import com.alican.mvvm_starter.domain.model.Loading
import com.alican.mvvm_starter.domain.model.Success
import com.alican.mvvm_starter.util.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FavoriteMoviesUseCase @Inject constructor(
    private val repository: FavoriteMoviesRepository
) {
    suspend  operator fun invoke(searchQuery: String = ""): Flow<BaseUIModel<List<FavoritesEntity>>> {
        return flow {
            emit(Loading())
            when (val result = repository.getFavoriteMovies(searchQuery)) {
                is ResultWrapper.GenericError -> {
                    emit(Error("Could not retrive the data from db"))
                }

                ResultWrapper.NetworkError -> {
                    emit(Error("Network error occured"))
                }

                is ResultWrapper.Success -> {
                    emit(Success(result.value))
                }
                else -> {}
            }
        }
    }
}