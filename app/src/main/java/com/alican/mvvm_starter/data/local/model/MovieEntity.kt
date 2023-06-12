package com.alican.mvvm_starter.data.local.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alican.mvvm_starter.util.Constant
import kotlinx.parcelize.Parcelize

@Entity(
    tableName = "movies"
)
@Parcelize
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val adult: Boolean?,
    val backdrop_path: String?,
    val movie_id: Int,
    val original_language: String,
    val overview: String?,
    val popularity: Float?,
    val poster_path: String?,
    val release_date: String?,
    val title: String,
    val video: Boolean?,
    val vote_average: Double?,
    val vote_count: Int?
) : Parcelable {

    fun getImagePath() : String {
        return Constant.BASE_POSTER_URL + poster_path
    }
}