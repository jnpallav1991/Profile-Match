package com.shaadi.smatch.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {

    companion object {

        private const val BASE_URL = "https://randomuser.me/"
    }

    private var apiService: ApiInterface

    private val httpClient = OkHttpClient.Builder()

    private val interceptor = HttpLoggingInterceptor()

    constructor() {
        //.readTimeout(60, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS)
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(interceptor).connectTimeout(100, TimeUnit.SECONDS)

        httpClient.addInterceptor { chain ->
            val request =
                chain.request().newBuilder().build()
            chain.proceed(request)
        }

        val retrofit by lazy {
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build()).build()
        }

        apiService = retrofit.create(ApiInterface::class.java)
    }


    fun getRestInterface(): ApiInterface {
        return apiService
    }
}