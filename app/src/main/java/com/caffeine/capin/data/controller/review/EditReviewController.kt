package com.caffeine.capin.data.controller.review

import com.caffeine.capin.data.network.BaseResponse
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody

interface EditReviewController{
    fun deleteReview(reviewId: String):Completable
    fun modifyReview(reviewId: String, review: MultipartBody.Part, imgs: List<MultipartBody.Part?>?): Single<BaseResponse>

}