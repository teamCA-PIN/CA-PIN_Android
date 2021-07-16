package com.caffeine.capin.review.all

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.caffeine.capin.network.CapinApiService
import com.caffeine.capin.network.enqueue
import com.caffeine.capin.review.CafeReview
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllCafeReviewsViewModel @Inject constructor(
    private val capinService: CapinApiService,
) : ViewModel() {
    val isPhotoFiltered = MutableLiveData(false)

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _cafeReviews = MutableLiveData<List<CafeReview>>()
    val cafeReviews: LiveData<List<CafeReview>> = _cafeReviews

    private val _reviewCount = MutableLiveData(0)
    val reviewCount: LiveData<Int> = _reviewCount

    private val reviewsCache = mutableListOf<CafeReview>()

    fun loadCafeDetails(cafeId: String) {
        _isLoading.value = true
        capinService.getCafeReviewsOf(cafeId).enqueue(
            onSuccess = { onSuccessLoadReviews(it.toCafeReviews()) },
            onFailure = {
                Log.d("MalibinDebug", "getCafeReviewsOf response : $it")
                _isLoading.value = false
            },
        )
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
