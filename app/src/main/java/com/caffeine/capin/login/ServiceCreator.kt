package com.caffeine.capin.login

import com.caffeine.capin.cafeti.CafetiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {
    private const val BASE_URL="http://3.37.75.200:5000"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val loginService: LoginService = retrofit.create(LoginService::class.java)
    val signUpService: SignUpService = retrofit.create(SignUpService::class.java)
    val cafetiService: CafetiService = retrofit.create(CafetiService::class.java)

}