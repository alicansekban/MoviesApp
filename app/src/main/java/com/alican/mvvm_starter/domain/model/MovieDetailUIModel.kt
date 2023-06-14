package com.alican.mvvm_starter.domain.model

import android.os.Parcelable
import com.alican.mvvm_starter.util.Constant
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetailUIModel(
    val id: Int?,
    val title: String?,
    val originalTitle: String?,
    val posterPath: String?,
    val backdropPath: String?,
    val overview: String?,
    val releaseDate: String?,
    val voteAverage: Double?,
    val voteCount: Int?,
    val popularity: Double?,
    val budget: Int?,
    val revenue: Int?,
    val runtime: Int?,
    val originalLanguage: String?,
    val imdbId: String?,
    val video: Boolean?,
    val adult: Boolean?,
    val homepage: String?,
    val status: String?,
    val genres: List<Genre>?,
    val spokenLanguages: List<SpokenLanguage>?,
    val productionCountries: List<ProductionCountry>?,
    val productionCompanies: List<ProductionCompany>?,
    val belongsToCollection: BelongsToCollection?
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

@Parcelize
data class SpokenLanguage(
    val iso6391: String?,
    val name: String?,
    val englishName: String?
) : Parcelable

@Parcelize
data class ProductionCountry(
    val iso31661: String?,
    val name: String?
) : Parcelable

@Parcelize
data class ProductionCompany(
    val id: Int?,
    val name: String?,
    val logoPath: String?,
    val originCountry: String?
):Parcelable

@Parcelize
data class BelongsToCollection(
    val id: Int?,
    val name: String?,
    val posterPath: String?,
    val backdropPath: String?
) : Parcelable


