package com.caffeine.capin.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created By Malibin
 * on 7월 15, 2021
 */

open class BaseViewModel : ViewModel() {
    protected val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    protected fun changeLoading(state: Boolean) {
        _isLoading.value = state
    }
}
