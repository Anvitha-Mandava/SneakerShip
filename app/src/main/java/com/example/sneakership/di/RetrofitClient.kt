package com.example.sneakership.di

import com.example.sneakership.api.SneakersService
import com.example.sneakership.helper.getContext
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val interceptor: Interceptor = Interceptor { chain ->
        val json = getContext().applicationContext?.assets?.open("SneakersRawData.json")
                ?.bufferedReader().use { it?.readText() }
        val mediaType = MediaType.parse("application/json")
        val responseBody = json?.let { ResponseBody.create(mediaType, it) }
        Response.Builder().code(200).protocol(Protocol.HTTP_1_1).message("OK").body(responseBody)
            .request(chain.request()).build()
    }

    private val okHttpClient: OkHttpClient =
        OkHttpClient.Builder().addInterceptor(interceptor).build()


    private val retrofit: Retrofit by lazy {
        Retrofit.Builder().baseUrl("http://sneakership.base.url/")
            .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()
    }

    val sneakersService: SneakersService by lazy {
        retrofit.create(SneakersService::class.java)
    }
}