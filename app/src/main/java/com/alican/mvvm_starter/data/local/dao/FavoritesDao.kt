package com.alican.mvvm_starter.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alican.mvvm_starter.data.local.model.FavoritesEntity

@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovie(movie: FavoritesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovies(movies: List<FavoritesEntity>)

    @Query("delete from favorite_movies")
    suspend fun clearFavoriteMovies()


    @Query("select * from favorite_movies order by id asc")
    fun getFavoriteMovies() : List<FavoritesEntity>
}