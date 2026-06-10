package com.example.songkpop_compose.core.network

import retrofit2.Response

interface SafeApiCall {
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): ApiResult<T> {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    ApiResult.Success(body)
                } else {
                    ApiResult.Error(response.code(), "Response body kosong")
                }
            } else {
                ApiResult.Error(response.code(), response.errorBody()?.string() ?: "Terjadi kesalahan")
            }
        } catch (e: Exception) {
            ApiResult.Exception(e)
        }
    }
}