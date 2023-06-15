package com.alican.mvvm_starter.domain.model

import android.os.Parcelable
import com.alican.mvvm_starter.util.Constant
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetailUIModel(
    val id: Int?,
    val title: String?,
    val posterPath: String?,
    val overview: String?,
    val releaseDate: String?,
    val voteAverage: Double?,
    val genres: List<Genre>?,
) : Parcelable {
    fun getImagePath(): String {
        return Constant.BASE_POSTER_URL + posterPath
    }
    fun getGenreTexts() : String {
        var text = ""
        genres?.forEach {
            text += it.name + ","
        }
        return text
    }
}

@Parcelize
data class Genre(
    val id: Int?,
    val name: String?
) : Parcelable

