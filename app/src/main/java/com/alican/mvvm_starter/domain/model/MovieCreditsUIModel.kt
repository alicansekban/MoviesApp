package com.alican.mvvm_starter.domain.model

data class MovieCreditsUIModel(
    val id: Int?,
    val cast: List<Cast>?,
    val crew: List<Crew>?
)

data class Cast(
    val castId: Int?,
    val character: String?,
    val gender: Int?,
    val creditId: String?,
    val knownForDepartment: String?,
    val originalName: String?,
    val popularity: Any?,
    val name: String?,
    val profilePath: String?,
    val id: Int?,
    val adult: Boolean?,
    val order: Int?
)

data class Crew(
    val gender: Int?,
    val creditId: String?,
    val knownForDepartment: String?,
    val originalName: String?,
    val popularity: Any?,
    val name: String?,
    val profilePath: String?,
    val id: Int?,
    val adult: Boolean?,
    val department: String?,
    val job: String?
)

