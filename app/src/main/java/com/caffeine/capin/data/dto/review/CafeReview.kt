package com.caffeine.capin.data.dto.review

import com.caffeine.capin.presentation.review.Recommend
import java.util.*

data class CafeReview(
    val reviewId: String,
    val cafeId: String,
    val profileImageUrl: String?,
    val writerId: String,
    val writerNickname: String,
    val createDate: Date,
    val recommend: List<Recommend>,
    val content: String,
    val starRate: Double,
    val type: String,
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
        val diffCount = imageUrls.size - maxImageCount + 1
        return if (diffCount < 0) 0 else diffCount
    }

    companion object {
        const val MAX_BASIC_REVIEW_IMAGE_COUNT = 3
        const val MAX_MYPAGE_REVIEW_IMAGE_COUNT = 3
    }
}
