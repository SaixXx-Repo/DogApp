package com.my.dogapp.data.remote.response


data class DogApiBaseResponse<T>(
    val message: T? = null,
    val status: String
)
