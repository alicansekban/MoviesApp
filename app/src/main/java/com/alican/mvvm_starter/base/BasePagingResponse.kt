package com.alican.mvvm_starter.base



data class BasePagingResponse<T>(
    val page: Int,
    val results: List<T>,
    val total_pages: Int,
    val total_results: Int
)