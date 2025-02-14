package com.mtc_hack.chat.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitClient {

    @POST("")
    suspend fun sendMessage(
        @Query("message") message: String,
    ): ChatMessage

    companion object {
        fun create(): RetrofitClient {
            return Retrofit.Builder().baseUrl("https://google.com")
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(RetrofitClient::class.java)
        }
    }
}