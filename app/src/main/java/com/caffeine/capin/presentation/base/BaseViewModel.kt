package com.caffeine.capin.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    protected val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    protected fun changeLoading(state: Boolean) {
        _isLoading.value = state
    }
}
