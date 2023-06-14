package com.alican.mvvm_starter.data.model

import com.squareup.moshi.Json

data class MovieReviewResponse(
    @Json(name = "author_details")
    val author_details: AuthorDetails? = null,

    @Json(name = "updated_at")
    val updated_at: String? = null,

    @Json(name = "author")
    val author: String? = null,

    @Json(name = "created_at")
    val created_at: String? = null,

    @Json(name = "id")
    val id: String? = null,

    @Json(name = "content")
    val content: String? = null,

    @Json(name = "url")
    val url: String? = null
)

data class AuthorDetails(

    @Json(name = "avatar_path")
    val avatar_path: String? = null,

    @Json(name = "name")
    val name: String? = null,

    @Json(name = "rating")
    val rating: Double? = null,

    @Json(name = "username")
    val username: String? = null
)