package com.example.songkpop_compose.core.network

sealed interface ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>
    data class Error(val code: Int, val message: String?) : ApiResult<Nothing>
    data class Exception(val throwable: Throwable) : ApiResult<Nothing>
}