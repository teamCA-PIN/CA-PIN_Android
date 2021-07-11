package com.caffeine.capin.review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WriteReviewViewModel: ViewModel() {
    val rateOfReview = MutableLiveData<Float>(0.0f)
    val contentsOfReview = MutableLiveData<String>()
}