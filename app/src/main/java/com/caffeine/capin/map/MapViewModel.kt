package com.caffeine.capin.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.naver.maps.map.overlay.Marker
import java.util.*
import kotlin.collections.ArrayList

class MapViewModel : ViewModel() {
    private val _cafeInformation = MutableLiveData<List<CafeInformationEntity>>()
    val cafeInformation: LiveData<List<CafeInformationEntity>>
        get() = _cafeInformation

    private val markerList = ArrayList<Marker>()

    private val _exposedMarker = MutableLiveData<ArrayList<Marker>>()
    val exposedMarker: LiveData<ArrayList<Marker>>
        get() = _exposedMarker

    private val _selectedMarker = MutableLiveData<Marker>()
    val selectedMarker: LiveData<Marker>
        get() = _selectedMarker

    private val _cafeInsideCurrentCamera = MutableLiveData<MutableList<CafeInformationEntity>>()
    val cafeInsideCurrentCamera: LiveData<MutableList<CafeInformationEntity>>
        get() = _cafeInsideCurrentCamera

    private val currentCafeList =  mutableListOf<CafeInformationEntity>()

    private val _selectedCafeInfo = MutableLiveData<CafeInformationEntity>()
    val selectedCafeInfo: LiveData<CafeInformationEntity>
        get() = _selectedCafeInfo

    private val cafeList111 = mutableMapOf<CafeInformationEntity, Boolean>()

    private val _cafeSelectedStatus = MutableLiveData<MutableMap<CafeInformationEntity, Boolean>>()
    val cafeSelectedStatus: LiveData<MutableMap<CafeInformationEntity, Boolean>>
        get() = _cafeSelectedStatus

    fun selectedStatus(cafeList: MutableMap<CafeInformationEntity, Boolean>) {
        _cafeSelectedStatus.value = cafeList
    }

    fun switchToCapinMap() {
        cafeList111.clear()
        capinMapInfo.forEach { cafe ->
            cafeList111[cafe] = false
        }
        _cafeSelectedStatus.value = cafeList111
//        _cafeInformation.value = capinMapInfo
    }

    fun changeMapValue(key: CafeInformationEntity, isSelected: Boolean) {
        cafeList111.forEach{ cafeInfo ->
            cafeList111[cafeInfo.key] = false
        }
        cafeList111[key] = isSelected
        _cafeSelectedStatus.value = cafeList111
    }

    fun switchToMyMap() {
        cafeList111.clear()
        myMapInfo.forEach { cafe ->
            cafeList111[cafe] = false
        }
        _cafeSelectedStatus.value = cafeList111
//        _cafeInformation.value = myMapInfo
    }

    fun addExposedMarker(marker: Marker) {
        markerList.add(marker)
        _exposedMarker.value = markerList
    }

    fun clearExposedMarker() {
        _exposedMarker.value?.clear()
    }

    fun changeSelectedCafeInfo(cafe: CafeInformationEntity) {
        _selectedCafeInfo.value = cafe
    }

    fun changeSelectedMarker(marker: Marker?) {
        if (marker != null) {
            _selectedMarker.value = marker!!
        } else {
            _selectedMarker.value?.map = null
        }
    }

    fun addCafeInsideCurrentCamera(cafe: CafeInformationEntity) {
        currentCafeList.add(cafe)
        _cafeInsideCurrentCamera.value = currentCafeList
    }

    fun removeAllCafeCurrentCamera() {
        currentCafeList.clear()
        _cafeInsideCurrentCamera.value = currentCafeList
    }


    companion object {
        private val capinMapInfo: List<CafeInformationEntity> = listOf(
            CafeInformationEntity("상수동까페", 37.6611907030766, 126.94903181748968,"그냥 카페", "서울시 마포구 마포대로 11길 1층(염리동)", "https://github.com/SONPYEONGHWA.png", 4.0),
            CafeInformationEntity("오레노라멘",37.54769086344692, 126.91736228447192,"오레노 라멘", "서울시 마포구 마포대로 11길 1층(염리동)", "https://github.com/SONPYEONGHWA.png", 4.0),
            CafeInformationEntity("플라밍고",37.5598780330203, 126.92397328447223,"그냥 카페", "서울시 마포구 마포대로 11길 1층(염리동)", "https://github.com/SONPYEONGHWA.png", 4.0),
            CafeInformationEntity("아미고",37.5628956716561, 126.92529371146098,"분위기 좋은 카페", "서울시 마포구 마포대로 11길 1층(염리동)", "https://github.com/SONPYEONGHWA.png", 4.5)
        )

        private val myMapInfo: List<CafeInformationEntity> = listOf(
            CafeInformationEntity("후엘고",37.580221, 126.923442, "브런치 카페", "서울시 마포구 마포대로 11길 1층(염리동)", "https://github.com/SONPYEONGHWA.png", 5.0),
            CafeInformationEntity("빈플루",37.582996109622876, 126.91380431146156,"인스타 갬성", "서울시 마포구 마포대로 11길 1층(염리동)", "https://github.com/SONPYEONGHWA.png", 4.5)
        )
    }
}