package com.caffeine.capin.network.response

data class ResponseToken(
    val message: String,
    val tokens: Token
) {
    data class Token(
        val accessToken: String,
        val refreshToken: String?
    )
}