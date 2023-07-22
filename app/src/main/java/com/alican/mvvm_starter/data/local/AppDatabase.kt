package com.alican.mvvm_starter.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.alican.mvvm_starter.data.local.dao.FavoritesDao
import com.alican.mvvm_starter.data.local.dao.RemoteKeyDao
import com.alican.mvvm_starter.data.local.dao.ReviewsDao
import com.alican.mvvm_starter.data.local.model.AuthorDetails
import com.alican.mvvm_starter.data.local.model.FavoritesEntity
import com.alican.mvvm_starter.data.local.model.RemoteKey
import com.alican.mvvm_starter.data.local.model.ReviewsEntity
import com.google.gson.Gson

@Database(entities = [ReviewsEntity::class,RemoteKey::class,FavoritesEntity::class], version = 1)
@TypeConverters(AuthorDetailsTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun reviewsDao(): ReviewsDao
    abstract fun remoteKeyDao(): RemoteKeyDao
    abstract fun favoritesDao(): FavoritesDao

}

class AuthorDetailsTypeConverter {
    @TypeConverter
    fun fromAuthorDetails(authorDetails: AuthorDetails?): String? {
        return Gson().toJson(authorDetails)
    }

    @TypeConverter
    fun toAuthorDetails(authorDetailsJson: String?): AuthorDetails? {
        return Gson().fromJson(authorDetailsJson, AuthorDetails::class.java)
    }
}

