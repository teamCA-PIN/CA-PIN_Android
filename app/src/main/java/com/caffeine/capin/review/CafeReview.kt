package com.caffeine.capin.review

import java.util.*

/**
 * Created By Malibin
 * on 7월 09, 2021
 */

data class CafeReview(
    val id: String,
    val profileImageUrl: String,
    val writerNickname: String,
    val createDate: Date,
    val recommend: List<Recommend>,
    val content: String,
    val starRate: Double,
    val type: String, //Fixme: F형, B형 CAFETI Enum으로 만들어져 있다면 그걸 활용할 것.
    val isMine: Boolean,
    val imageUrls: List<String>
) {
    fun hasPhoto(): Boolean = imageUrls.isNotEmpty()

    fun getRecommendStrings(): List<String> = recommend.map { it.description }

    fun getReviewImageUrlFirst(): String? = imageUrls.getOrNull(0)

    fun getReviewImageUrlSecond(): String? = imageUrls.getOrNull(1)

    fun getReviewImageUrlThird(): String? = imageUrls.getOrNull(2)

    fun getReviewImageUrlFourth(): String? = imageUrls.getOrNull(3)

    fun getImageOverCount(maxImageCount: Int): Int {
        val diffCount = imageUrls.size - maxImageCount
        return if (diffCount < 0) 0 else diffCount
    }

    companion object {
        const val MAX_BASIC_REVIEW_IMAGE_COUNT = 4
        const val MAX_MYPAGE_REVIEW_IMAGE_COUNT = 3
    }
}
