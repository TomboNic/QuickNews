package com.example.quicknews

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val retrofit = Retrofit.Builder().baseUrl("https://api.mediastack.com/v1/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val NewsService = retrofit.create(ApiService::class.java)

interface ApiService{

    @GET("news")
    suspend fun getNews(
        @Query("access_key") apiKey: String = "3246856e86f1b7f684ffb12020622be6",
        @Query("countries") country: String?,
        @Query("categories") category: String?,
        @Query("languages") language: String? = "en"
    ): NewsResponse

}