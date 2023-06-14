package com.alican.mvvm_starter.domain.model

data class MovieDetailReviewsUIModel(
    val id: String?,
    val author: String?,
    val content: String?,
    val url: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val authorDetails: AuthorDetails?
)

data class AuthorDetails(
    val avatarPath: String?,
    val name: String?,
    val rating: Double?,
    val username: String?
)

