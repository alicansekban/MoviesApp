package com.alican.mvvm_starter.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.alican.mvvm_starter.data.local.model.DataModel
import com.alican.mvvm_starter.data.local.model.MovieEntity
import kotlinx.coroutines.flow.Flow

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

    @Query("select * from movies where title like '%' || :searchQuery || '%' order by id asc")
    fun getPagingMovieQuery(searchQuery: String) : PagingSource<Int, MovieEntity>

    @Query("select * from movies order by id asc limit :limit")
    fun getFlowMovies(limit: Int): Flow<List<MovieEntity>>
}
