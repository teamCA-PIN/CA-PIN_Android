package com.caffeine.capin.data.dto.cafeti

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class ResponseCafetiData(
    val message: String,
    val result: Result
): Parcelable {
    @Parcelize
    data class Result (
        val cafetiIdx: String,
        val type: String,
        val modifier: String,
        val modifierDetail: String?,
        val introduction : String,
        val img: String,
        val plainImg: String
    ): Parcelable
}
