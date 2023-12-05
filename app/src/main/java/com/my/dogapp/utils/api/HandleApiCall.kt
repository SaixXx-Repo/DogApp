package com.my.dogapp.utils.api

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun <T> handleApiCall(onResult: (ApiResponse<T>) -> Unit, call: Call<T>) {
    onResult(ApiResponse.Loading(true))
    call.enqueue(object : Callback<T> {
        override fun onResponse(
            call: Call<T>,
            response: Response<T>
        ) {
            if (response.isSuccessful) {
                val postResponse = response.body()
                if (postResponse != null) {
                    onResult(ApiResponse.Success(postResponse))
                } else {
                    onResult(ApiResponse.Error("Response body is null"))
                }
            } else {
                onResult(ApiResponse.Error("Failed to fetch data"))
            }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            onResult(ApiResponse.Error(t.message ?: "Unknown error"))
        }
    })
}
