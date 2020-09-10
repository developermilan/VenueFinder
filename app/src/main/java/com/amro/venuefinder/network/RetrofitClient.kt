package com.amro.venuefinder.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl(NetworkConfig.baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(getClient())
        .build()

    private fun getClient(): OkHttpClient {
        val interceptor = CustomItercepter()
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(interceptor)
        return builder.build()
    }

    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}