package com.caffeine.capin.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.caffeine.capin.map.entity.CafetiEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MapProfileViewModel @Inject constructor(
    private val userProfileRepository: UserProfileRepository
): ViewModel() {
    private val _userInfo = MutableLiveData<UserEntity>()
    val userInfo: LiveData<UserEntity>
        get() = _userInfo

    init {
        getUserInfo()
    }

    private fun getUserInfo() {
        userProfileRepository.getUserProfile()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _userInfo.postValue(it)
            }, {
                it.printStackTrace()
            })
    }
}