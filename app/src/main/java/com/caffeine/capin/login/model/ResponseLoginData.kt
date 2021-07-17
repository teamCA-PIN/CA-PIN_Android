package com.caffeine.capin.login.model

data class ResponseLoginData(
    val message: String,
    val loginData: LoginData
){
    data class LoginData (
        val nickname: String,
        val token: String
    )
}
