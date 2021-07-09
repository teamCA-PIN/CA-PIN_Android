package com.caffeine.capin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {
    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean>
        get()=_isEmpty

    fun changeIsEmpty(empty: Boolean) {
        _isEmpty.value = empty
    }
}