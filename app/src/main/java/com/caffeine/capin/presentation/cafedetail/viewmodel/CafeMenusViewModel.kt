package com.caffeine.capin.presentation.cafedetail.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.caffeine.capin.domain.entity.cafedetail.CafeMenuEntity
import com.caffeine.capin.data.network.CapinApiService
import com.caffeine.capin.data.network.enqueue
import com.caffeine.capin.data.dto.cafedetail.CafeMenusResponse
import com.caffeine.capin.data.dto.base.CapinFailResponse
import com.caffeine.capin.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created By Malibin
 * on 7월 14, 2021
 */

@HiltViewModel
class CafeMenusViewModel @Inject constructor(
    private val capinService: CapinApiService
) : BaseViewModel() {

    private val _cafeMenus = MutableLiveData<List<CafeMenuEntity>>()
    val cafeMenusEntity: LiveData<List<CafeMenuEntity>> = _cafeMenus

    fun loadCafeMenus(cafeId: String) {
        _isLoading.value = true
        capinService.getCafeMenus(cafeId).enqueue(
            onSuccess = ::onLoadCafeMenuSuccess,
            onFailure = ::onLoadCafeMenuFailure,
        )
    }

    private fun onLoadCafeMenuSuccess(response: CafeMenusResponse) {
        _cafeMenus.value = response.getCafeMenus()
        changeLoading(false)
    }

    private fun onLoadCafeMenuFailure(response: CapinFailResponse) {
        changeLoading(false)
        Log.d("MalibinDebug", "onLoadCafeMenuFailure resposen : $response")
    }

    companion object {
        private val STUB_CAFE_MENUS = listOf(
            CafeMenuEntity(
                name = "에스프레소",
                price = 3500,
            ),
            CafeMenuEntity(
                name = "아메리카노",
                price = 4000,
            ),
            CafeMenuEntity(
                name = "카페라떼",
                price = 4500,
            ),
            CafeMenuEntity(
                name = "유니콘 뿔",
                price = 80_000_000,
            ),
            CafeMenuEntity(
                name = "지옥 참마도",
                price = Int.MAX_VALUE,
            ),
        )
    }
}
