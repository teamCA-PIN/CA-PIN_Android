package com.caffeine.capin.detail

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.caffeine.capin.network.CapinApiService
import com.caffeine.capin.network.enqueue
import com.caffeine.capin.review.CafeReview
import com.caffeine.capin.review.Recommend
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CafeDetailsViewModel @Inject constructor(
    private val capinService: CapinApiService,
) : ViewModel() {

    private val _isDetailsLoading = MutableLiveData(false)
    val isDetailsLoading: LiveData<Boolean> = _isDetailsLoading

    private val _cafeDetails = MutableLiveData<CafeDetails>()
    val cafeDetails: LiveData<CafeDetails> = _cafeDetails

    private val _isReviewsLoading = MutableLiveData(false)
    val isReviewsLoading: LiveData<Boolean> = _isReviewsLoading

    private val _cafeReviews = MutableLiveData<List<CafeReview>>()
    val cafeReviews: LiveData<List<CafeReview>> = _cafeReviews

    private val _isReviewWritten = MutableLiveData<String>()
    val isReviewWritten: LiveData<String> = _isReviewWritten

    fun updateReviewWritten(review: String) {
        _isReviewWritten.value = review
    }

    fun loadCafeDetails(cafeId: String) {
        _isDetailsLoading.value = true
        _isReviewsLoading.value = true
        capinService.getCafeDetail(cafeId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onSuccessLoadDetails(it.toCafeDetails()) },
                ::onServiceFailure,
            )

        capinService.getCafeReviewsOf(cafeId).enqueue(
            onSuccess = { onSuccessLoadReviews(it.toCafeReviews()) },
            onFailure = {
                _isReviewsLoading.value = false
            },
        )
    }

    private fun onSuccessLoadDetails(details: CafeDetails) {
        _cafeDetails.value = details
        _isDetailsLoading.value = false
    }

    private fun onSuccessLoadReviews(reviews: List<CafeReview>) {
        _cafeReviews.value = reviews.take(2)
        _isReviewsLoading.value = false
    }

    private fun onServiceFailure(t: Throwable) {
        _isDetailsLoading.value = false
    }
}
