package com.caffeine.capin.login.model

data class ResponseLoginData(
    val message: String,
    val loginData: LoginData
){
    data class LoginData (
        val nickname: String,
        val token_access: String,
        val token_refresh: String
    )
}
