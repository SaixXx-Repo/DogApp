package com.my.dogapp.di.module

import com.my.dogapp.data.remote.api.DogApi
import com.my.dogapp.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideDogApi(): DogApi {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        val gson = GsonConverterFactory.create()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gson)
            .client(client)
            .build()
            .create(DogApi::class.java)
    }
}
