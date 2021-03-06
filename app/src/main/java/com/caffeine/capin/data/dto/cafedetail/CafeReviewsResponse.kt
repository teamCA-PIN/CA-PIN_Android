package com.caffeine.capin.data.dto.cafedetail

import com.caffeine.capin.data.dto.review.CafeReview
import com.caffeine.capin.presentation.review.Recommend
import java.text.SimpleDateFormat
import java.util.*

data class CafeReviewsResponse(
    val message: String,
    val reviews: List<Item>?,
    val isReviewed: Boolean,
) {
    fun toCafeReviews(): List<CafeReview> = this.reviews?.map { it.toCafeReview() }.orEmpty()

    data class Item(
        val _id: String,
        val cafeId: String,
        val writer: Writer,
        val rating: Double,
        val created_at: String,
        val content: String,
        val recommend: List<Int>?,
        val imgs: List<String>?,
    ) {
        fun toCafeReview() = CafeReview(
            reviewId = this._id,
            cafeId = this.cafeId,
            profileImageUrl = this.writer.profileImg,
            writerId = writer._id,
            writerNickname = this.writer.nickname,
            createDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.KOREA)
                .parse(this.created_at)!!,
            recommend = this.recommend?.map { Recommend.find(it) }.orEmpty(),
            content = this.content,
            starRate = this.rating,
            type = "",
            isMine = false,
            imageUrls = imgs.orEmpty(),
        )
    }

    data class Writer(
        val _id: String,
        val nickname: String,
        val profileImg: String?,
    )
}
