package com.caffeine.capin.review

/**
 * Created By Malibin
 * on 7월 09, 2021
 */

enum class Recommend(
    private val id: Int,
    val description: String,
) {
    MOOD(0, "분위기 추천"),
    TASTE(1, "맛 추천");
}
