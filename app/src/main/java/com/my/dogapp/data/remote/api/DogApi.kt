package com.my.dogapp.data.remote.api

import com.my.dogapp.data.remote.response.DogApiBaseResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DogApi {

    @GET("breeds/list/all")
    fun getAllBreeds(): Call<DogApiBaseResponse<Map<String,List<String>>>>

    @GET("breed/{breed}/images")
    fun getBreedImages(@Path("breed") breed: String): Call<DogApiBaseResponse<List<String>>>

    @GET("breed/{breed}/{subBreed}/images")
    fun getSubBreedImages(@Path("breed") breed: String, @Path("subBreed") subBreed: String): Call<DogApiBaseResponse<List<String>>>
}