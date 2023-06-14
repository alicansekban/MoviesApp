package com.alican.mvvm_starter.domain.model

data class MovieDetailUIModel(
    val id: Int?,
    val title: String?,
    val originalTitle: String?,
    val posterPath: String?,
    val backdropPath: String?,
    val overview: String?,
    val releaseDate: String?,
    val voteAverage: Any?,
    val voteCount: Int?,
    val popularity: Any?,
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
) {
    data class Genre(
        val id: Int?,
        val name: String?
    )

    data class SpokenLanguage(
        val iso6391: String?,
        val name: String?,
        val englishName: String?
    )

    data class ProductionCountry(
        val iso31661: String?,
        val name: String?
    )

    data class ProductionCompany(
        val id: Int?,
        val name: String?,
        val logoPath: String?,
        val originCountry: String?
    )

    data class BelongsToCollection(
        val id: Int?,
        val name: String?,
        val posterPath: String?,
        val backdropPath: String?
    )
}

