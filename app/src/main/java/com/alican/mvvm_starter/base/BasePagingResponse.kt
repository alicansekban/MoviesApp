package com.alican.mvvm_starter.base

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class BasePagingResponse<T>(
    @Json(name = "items") val items: List<T> = arrayListOf()
)