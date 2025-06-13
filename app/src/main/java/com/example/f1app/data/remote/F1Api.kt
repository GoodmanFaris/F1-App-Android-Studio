package com.example.f1app.data.remote


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.Interceptor

object F1Api {
    private const val BASE_URL = "https://api.openf1.org/"

    private val client = OkHttpClient.Builder()
        .addInterceptor(Interceptor { chain ->
            val request = chain.request().newBuilder()
                .header("User-Agent", "F1App-Android")
                .build()
            chain.proceed(request)
        })
        .build()

    val retrofitService: F1ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(F1ApiService::class.java)
    }
}
