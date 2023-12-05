package com.my.dogapp.utils.api

sealed class ApiResponse<T> {
    data class Loading<T>(val loading: Boolean) : ApiResponse<T>()
    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Error<T>(val errorMessage: String) : ApiResponse<T>()
}
