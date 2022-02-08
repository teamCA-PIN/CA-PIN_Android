package com.caffeine.capin.presentation.cafeti.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.caffeine.capin.data.dto.cafeti.ResponseCafetiData

class CafetiResultViewModel: ViewModel() {
    private val _cafetiResult = MutableLiveData<ResponseCafetiData.Result>()
    val cafetiResult: LiveData<ResponseCafetiData.Result> = _cafetiResult

    fun changeCafetiResult(result: ResponseCafetiData.Result) {
        _cafetiResult.value = result
    }
}