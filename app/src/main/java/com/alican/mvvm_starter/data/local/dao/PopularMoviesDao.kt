package com.alican.mvvm_starter.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alican.mvvm_starter.data.local.model.MovieEntity

@Dao
interface NowPlayingMoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("delete from movies")
    suspend fun clearMovies()

    @Query("select * from movies order by id asc")
    fun getPagingMovie() : PagingSource<Int, MovieEntity>
}
