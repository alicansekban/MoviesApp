package com.alican.mvvm_starter.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alican.mvvm_starter.data.local.dao.FavoriteMoviesDao
import com.alican.mvvm_starter.data.local.dao.NowPlayingMoviesDao
import com.alican.mvvm_starter.data.local.dao.RemoteKeyDao
import com.alican.mvvm_starter.data.local.model.DataModel
import com.alican.mvvm_starter.data.local.model.FavoriteMovieEntity
import com.alican.mvvm_starter.data.local.model.MovieEntity
import com.alican.mvvm_starter.data.local.model.RemoteKey

@Database(entities = [MovieEntity::class,RemoteKey::class,FavoriteMovieEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun homeMoviesDao(): NowPlayingMoviesDao
    abstract fun remoteKeyDao(): RemoteKeyDao

    abstract fun favoriteMoviesDAo() : FavoriteMoviesDao
}
