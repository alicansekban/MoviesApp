package com.alican.mvvm_starter.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorite_movies"
)
data class FavoritesEntity(
    @PrimaryKey
    var id: Int = 0,
    var title: String,
    var posterPath: String,
    var overview: String,
    var releaseDate: String?,
    var voteAverage: Double,
)