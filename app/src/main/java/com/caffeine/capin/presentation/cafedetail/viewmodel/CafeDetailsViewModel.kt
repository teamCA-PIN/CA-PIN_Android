package com.caffeine.capin.presentation.cafedetail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.caffeine.capin.domain.entity.cafedetail.CafeDetailsEntity
import com.caffeine.capin.data.network.CapinApiService
import com.caffeine.capin.data.network.enqueue
import com.caffeine.capin.data.dto.review.CafeReview
import com.caffeine.capin.domain.usecase.DeleteReviewUseCase
import com.caffeine.capin.domain.usecase.ReportReviewUseCase
import com.caffeine.capin.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class CafeDetailsViewModel @Inject constructor(
    private val capinService: CapinApiService,
    private val reportReviewUseCase: ReportReviewUseCase,
    private val deleteReviewUseCase: DeleteReviewUseCase
    ) : BaseViewModel() {

    private val _isDetailsLoading = MutableLiveData(false)
    val isDetailsLoading: LiveData<Boolean> = _isDetailsLoading

    private val _cafeDetails = MutableLiveData<CafeDetailsEntity>()
    val cafeDetailsEntity: LiveData<CafeDetailsEntity> = _cafeDetails

    private val _isReviewsLoading = MutableLiveData(false)
    val isReviewsLoading: LiveData<Boolean> = _isReviewsLoading

    private val _cafeReviews = MutableLiveData<List<CafeReview>>()
    val cafeReviews: LiveData<List<CafeReview>> = _cafeReviews

    private val _isReviewWritten = MutableLiveData<String>()
    val isReviewWritten: LiveData<String> = _isReviewWritten

    private val _isDeleteSuccess = MutableLiveData<Boolean>()
    val isDeleteSuccess: LiveData<Boolean> = _isDeleteSuccess

    fun updateReviewWritten(review: String) {
        _isReviewWritten.value = review
    }

    fun removeReviewData(cafeReview: CafeReview) {
        val reviews = cafeReviews.value?.toMutableList() ?: mutableListOf()
        reviews.remove(cafeReview)
        _cafeReviews.value = reviews
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

    private fun onSuccessLoadDetails(detailsEntity: CafeDetailsEntity) {
        _cafeDetails.value = detailsEntity
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
