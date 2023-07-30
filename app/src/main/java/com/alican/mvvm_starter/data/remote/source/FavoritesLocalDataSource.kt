package com.alican.mvvm_starter.data.remote.source

import com.alican.mvvm_starter.data.local.AppDatabase
import com.alican.mvvm_starter.data.local.model.FavoritesEntity
import com.alican.mvvm_starter.data.model.ErrorResponse
import com.alican.mvvm_starter.util.ResultWrapper
import javax.inject.Inject

class FavoritesLocalDataSource @Inject constructor(
    private val db: AppDatabase
) {

    suspend fun insertFavoriteMovie(movie: FavoritesEntity): ResultWrapper<Boolean> {
        return try {
            ResultWrapper.Loading
            db.favoritesDao().insertFavoriteMovie(movie)
            ResultWrapper.Success(true)
        } catch (e : Exception) {
            ResultWrapper.GenericError()
        }

    }

    fun getFavoriteMovies(searchQuery:String): ResultWrapper<List<FavoritesEntity>> {
        return try {
            ResultWrapper.Loading
            ResultWrapper.Success(db.favoritesDao().getFavoriteMovies(searchQuery))

        } catch (e: Exception) {
            ResultWrapper.GenericError(error = ErrorResponse(message = e.message))
        }
    }
}
