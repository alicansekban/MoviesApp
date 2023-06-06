
package com.alican.mvvm_starter.base

data class BaseResponse<T>(
    val success: Boolean,
    val message: String,
    val error: Error,
    val entity: T,
)

data class Error(val code: Int, val debugMessage: String, val message: String)