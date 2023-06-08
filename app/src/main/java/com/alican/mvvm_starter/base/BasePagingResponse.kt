package com.alican.mvvm_starter.base

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


data class BasePagingResponse<T>(
    val page: Int,
    val results: List<T>,
    val total_pages: Int,
    val total_results: Int
)