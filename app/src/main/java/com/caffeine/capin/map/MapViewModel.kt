package com.caffeine.capin.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.naver.maps.map.overlay.Marker
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class MapViewModel @Inject constructor(
    private val cafeListRepository: CafeListRepository
) : ViewModel() {
    private val markerList = ArrayList<Marker>()

    private val _exposedMarker = MutableLiveData<ArrayList<Marker>>()
    val exposedMarker: LiveData<ArrayList<Marker>>
        get() = _exposedMarker

    private val _cafeInsideCurrentCamera = MutableLiveData<Map<CafeInformationEntity, Boolean>>()
    val cafeInsideCurrentCamera: LiveData<Map<CafeInformationEntity, Boolean>>
        get() = _cafeInsideCurrentCamera

    private var cafeList = mutableMapOf<CafeInformationEntity, Boolean>()

    private val _selectedCafe = MutableLiveData<CafeDetailEntity>()
    val selectedCafe: LiveData<CafeDetailEntity>
        get() = _selectedCafe

    private val _cafeCurrentChecked = MutableLiveData<CafeInformationEntity>()
    val cafeCurrentChecked: LiveData<CafeInformationEntity>
        get() = _cafeCurrentChecked

    fun changeCafeCurrentChecked(cafe: CafeInformationEntity) {
        _cafeCurrentChecked.value = cafe
    }

    fun changeSelectedCafe(cafe: CafeDetailEntity) {
        _selectedCafe.value = cafe
    }

    private val _capinMapCafeLocations = MutableLiveData<List<CafeInformationEntity>>()
    val capinMapCafeLocation: LiveData<List<CafeInformationEntity>>
        get() = _capinMapCafeLocations

    fun getCapinMapCafeLocations() {
        cafeListRepository.getCafeList(null)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ cafes ->
                _capinMapCafeLocations.postValue(cafes)
                cafes.forEach { cafe ->
                    cafeList[cafe] = false
                }
                _cafeInsideCurrentCamera.postValue(cafeList)
            }, {
                it.printStackTrace()
            })
    }

    fun getSelectedCafeDetailInfo() {
        cafeInsideCurrentCamera.value?.forEach { isSelectedCafe ->
            if (isSelectedCafe.value) {
                cafeListRepository.getCafeDetail(isSelectedCafe.key.cafeId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        _selectedCafe.postValue(it)
                    }, {
                        it.printStackTrace()
                    })
            }
        }
    }

    fun switchToCapinMap() {
        cafeList.clear()
        getCapinMapCafeLocations()
    }

    fun switchToMyMap() {
        cafeList.clear()
        myMapInfo.forEach { cafe ->
            cafeList[cafe] = false
        }
        _cafeInsideCurrentCamera.value = cafeList
    }

    fun addExposedMarker(marker: Marker) {
        markerList.add(marker)
        _exposedMarker.value = markerList
    }

    fun clearExposedMarker() {
        _exposedMarker.value?.clear()
    }

    fun addCafeInsideCurrentCamera(key: CafeInformationEntity, isSelected: Boolean) {
        cafeList.forEach{
            cafeList[it.key] = false
        }
        cafeList[key] = isSelected
        _cafeInsideCurrentCamera.value = cafeList
        Log.e("cafeList", "${cafeInsideCurrentCamera.value}")
    }

    fun removeAllCafeCurrentCamera() {
        cafeList.clear()
        _cafeInsideCurrentCamera.value = cafeList
    }

    companion object {
        private val myMapInfo: List<CafeInformationEntity> = listOf(
            CafeInformationEntity("후엘고", 37.580221, 126.923442),
            CafeInformationEntity("빈플루", 37.582996109622876, 126.91380431146156)
        )
    }
}