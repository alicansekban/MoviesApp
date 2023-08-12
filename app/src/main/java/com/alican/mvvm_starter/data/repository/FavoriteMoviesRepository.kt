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

    fun getFavoriteMovies(searchQuery:String): ResultWrapper<List<FavoritesEntity>> {
        return localDataSource.getFavoriteMovies(searchQuery)
    }

    suspend fun removeMovieFromFavorites(id:Int): ResultWrapper<Any> {
        return localDataSource.removeMovieFromFavorites(id)
    }
}