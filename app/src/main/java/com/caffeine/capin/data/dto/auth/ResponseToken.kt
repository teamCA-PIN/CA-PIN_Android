package com.caffeine.capin.data.dto.auth

import com.google.gson.annotations.SerializedName

data class ResponseToken(
    val message: String,
    val tokens: Token
) {
    data class Token(
        @SerializedName("token_access")
        val accessToken: String,
        @SerializedName("token_refresh")
        val refreshToken: String?
    )
}