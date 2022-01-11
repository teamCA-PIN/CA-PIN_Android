package com.caffeine.capin.data.network

import com.caffeine.capin.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val capinApiService: CapinApiService = retrofit.create(CapinApiService::class.java)
}