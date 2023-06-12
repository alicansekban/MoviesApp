package com.alican.mvvm_starter.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alican.mvvm_starter.data.local.model.FavoriteMovieEntity
import com.alican.mvvm_starter.data.local.model.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: FavoriteMovieEntity)


    @Query("SELECT * FROM favorite_movies")
    fun getFavoriteMovies() : Flow<List<FavoriteMovieEntity>>
}