package com.caffeine.capin.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.caffeine.capin.data.dto.login.RequestLoginData
import com.caffeine.capin.data.network.TokenController
import com.caffeine.capin.data.local.UserPreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val tokenController: TokenController,
    private val userPreferenceManager: UserPreferenceManager
): ViewModel() {
    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean>
        get()=_isEmpty

    fun changeIsEmpty(empty: Boolean) {
        _isEmpty.value = empty
    }

    fun login() {
        userPreferenceManager.run {
            val email = getUserEmail()
            val password = getUserPassword()
            if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
                tokenController.login(
                    RequestLoginData(email, password)
                ).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        setUserAccessToken(it.loginData.token_access)
                        setUserRefreshToken(it.loginData.token_refresh)
                    }, {
                        it.printStackTrace()
                    })
            }
        }
    }
}