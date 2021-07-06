package com.caffeine.capin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MapViewModel : ViewModel() {
    private val _cafeInformation = MutableLiveData<List<CafeInformationEntity>>()
    val cafeInformation: LiveData<List<CafeInformationEntity>>
        get() = _cafeInformation

    fun switchToCapinMap() {
        _cafeInformation.value = capinMapInfo
    }

    fun switchToMyMap() {
        _cafeInformation.value = myMapInfo
    }

    companion object {
        private val capinMapInfo: List<CafeInformationEntity> = listOf(
            CafeInformationEntity(37.580221, 126.923442),
            CafeInformationEntity(37.582996109622876, 126.91380431146156),
            CafeInformationEntity(37.579256039764246, 126.92016389612074),
            CafeInformationEntity(37.556561236976535, 126.92786145335195)
        )

        private val myMapInfo: List<CafeInformationEntity> = listOf(
            CafeInformationEntity(37.5598780330203, 126.92397328447223),
            CafeInformationEntity(37.5628956716561, 126.92529371146098),
            CafeInformationEntity(37.56111453790112, 126.92340389420406),
            CafeInformationEntity(37.56216407332653, 126.92306471146108)
        )
    }
}