package com.caffeine.capin.detail.menus

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created By Malibin
 * on 7월 14, 2021
 */

@HiltViewModel
class CafeMenusViewModel @Inject constructor(

) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _cafeMenus = MutableLiveData<List<CafeMenu>>()
    val cafeMenus: LiveData<List<CafeMenu>> = _cafeMenus

    fun loadCafeMenus(cafeId: String) {
        _isLoading.value = true
        _cafeMenus.value = STUB_CAFE_MENUS
        _isLoading.value = false
    }

    companion object {
        private val STUB_CAFE_MENUS = listOf(
            CafeMenu(
                name = "에스프레소",
                price = 3500,
            ),
            CafeMenu(
                name = "아메리카노",
                price = 4000,
            ),
            CafeMenu(
                name = "카페라떼",
                price = 4500,
            ),
            CafeMenu(
                name = "유니콘 뿔",
                price = 80_000_000,
            ),
            CafeMenu(
                name = "지옥 참마도",
                price = Int.MAX_VALUE,
            ),
        )
    }
}
