package com.alican.mvvm_starter.data.repository

import com.alican.mvvm_starter.data.local.model.FavoritesEntity
import com.alican.mvvm_starter.data.remote.source.FavoritesLocalDataSource
import com.alican.mvvm_starter.util.ResultWrapper
import javax.inject.Inject

class FavoriteMoviesRepository @Inject constructor(
    private val localDataSource: FavoritesLocalDataSource
) {

    suspend fun insertFavoriteMovie(movie: FavoritesEntity) : ResultWrapper<Boolean> {
        return localDataSource.insertFavoriteMovie(movie = movie)
    }

    fun getFavoriteMovies(): ResultWrapper<List<FavoritesEntity>> {
        return localDataSource.getFavoriteMovies()
    }
}