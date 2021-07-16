package com.caffeine.capin.detail

import com.caffeine.capin.review.CafeReview

/**
 * Created By Malibin
 * on 7월 09, 2021
 */

data class CafeDetails(
    val id: String,
    val cafeName: String,
    val mainImageUrl: String?,
    val starRate: Float?,
    val address: String,
    val tags: List<String>,
    val instagramId: String,
    val operationTime: String,
    val reviews: List<CafeReview>,
)
