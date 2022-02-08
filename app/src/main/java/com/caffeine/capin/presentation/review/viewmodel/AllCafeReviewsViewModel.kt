package com.caffeine.capin.presentation.review.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.caffeine.capin.data.controller.review.EditReviewController
import com.caffeine.capin.data.network.CapinApiService
import com.caffeine.capin.data.network.enqueue
import com.caffeine.capin.data.dto.review.CafeReview
import com.caffeine.capin.domain.usecase.DeleteReviewUseCase
import com.caffeine.capin.domain.usecase.ReportReviewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class AllCafeReviewsViewModel @Inject constructor(
    private val capinService: CapinApiService,
    private val reportReviewUseCase: ReportReviewUseCase,
    private val deleteReviewUseCase: DeleteReviewUseCase
) : ViewModel() {
    val isPhotoFiltered = MutableLiveData(false)

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _cafeReviews = MutableLiveData<List<CafeReview>>()
    val cafeReviews: LiveData<List<CafeReview>> = _cafeReviews

    private val _reviewCount = MutableLiveData(0)
    val reviewCount: LiveData<Int> = _reviewCount

    private val _isDeleteSuccess = MutableLiveData<Boolean>()
    val isDeleteSuccess: LiveData<Boolean> = _isDeleteSuccess

    private val reviewsCache = mutableListOf<CafeReview>()

    fun loadCafeDetails(cafeId: String) {
        _isLoading.value = true
        capinService.getCafeReviewsOf(cafeId).enqueue(
            onSuccess = { onSuccessLoadReviews(it.toCafeReviews()) },
            onFailure = {
                _isLoading.value = false
            },
        )
    }

    fun removeReviewData(cafeReview: CafeReview) {
        val reviews = cafeReviews.value?.toMutableList() ?: mutableListOf()
        reviews.remove(cafeReview)
        _cafeReviews.value = reviews
    }

    fun reportReview(reviewId: String) {
        reportReviewUseCase(reviewId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, {
                it.printStackTrace()
            })
    }

    fun deleteReview(reviewId: String) {
        deleteReviewUseCase(reviewId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _isDeleteSuccess.postValue(true)
            }, {
                _isDeleteSuccess.postValue(false)
                it.printStackTrace()
            })
    }


    fun filterPhotoReviews() {
        if (isPhotoFiltered.value == true) {
            _cafeReviews.value = reviewsCache.filter { it.hasPhoto() }
            return
        }
        _cafeReviews.value = reviewsCache
    }

    private fun onSuccessLoadReviews(reviews: List<CafeReview>) {
        _cafeReviews.value = reviews
        _reviewCount.value = reviews.size
        reviewsCache.clear()
        reviewsCache.addAll(reviews)
        _isLoading.value = false
    }
}
