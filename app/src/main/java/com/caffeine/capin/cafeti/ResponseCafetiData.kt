package com.caffeine.capin.cafeti

import java.io.Serializable

data class ResponseCafetiData(
    val message: String,
    val result: Result
){
    data class Result (
        val cafetiIdx: String,
        val type: String,
        val modifier: String,
        val modifierDetail: String?,
        val img: String,
        val plainImg: String
    ): Serializable
}
