package com.caffeine.capin.map

import android.widget.CompoundButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.caffeine.capin.map.entity.CafeDetailEntity
import com.caffeine.capin.map.entity.CafeInformationEntity
import com.caffeine.capin.map.repository.CafeListRepository
import com.caffeine.capin.map.repository.MyMapLocationsRepository
import com.caffeine.capin.tagfilter.model.TagFilterEntity
import com.caffeine.capin.util.UiState
import com.naver.maps.map.overlay.Marker
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class MapViewModel @Inject constructor(
    private val cafeListRepository: CafeListRepository,
    private val myMapLocationsRepository: MyMapLocationsRepository
) : ViewModel() {
    private val markerList = ArrayList<Marker>()

    private val _isMyMap = MutableLiveData<Boolean>()
    val isMyMap: LiveData<Boolean>
        get() = _isMyMap

    private val _exposedMarker = MutableLiveData<ArrayList<Marker>>()
    val exposedMarker: LiveData<ArrayList<Marker>>
        get() = _exposedMarker

    private val _cafeInsideCurrentCamera = MutableLiveData<Map<CafeInformationEntity, Boolean>?>()
    val cafeInsideCurrentCamera: LiveData<Map<CafeInformationEntity, Boolean>?>
        get() = _cafeInsideCurrentCamera

    private var cafeList = mutableMapOf<CafeInformationEntity, Boolean>()

    private val _selectedCafe = MutableLiveData<UiState<CafeDetailEntity>>()
    val selectedCafe: LiveData<UiState<CafeDetailEntity>>
        get() = _selectedCafe

    private val _cafeCurrentChecked = MutableLiveData<CafeInformationEntity?>()
    val cafeCurrentChecked: LiveData<CafeInformationEntity?>
        get() = _cafeCurrentChecked

    private val _capinMapCafeLocations = MutableLiveData<List<CafeInformationEntity>>()
    val capinMapCafeLocation: LiveData<List<CafeInformationEntity>>
        get() = _capinMapCafeLocations

    init {
        if(isMyMap.value == true) {
            getMyMapPins()
        } else {
            getCapinMapPins()
        }
    }

    fun changeCafeCurrentChecked(cafe: CafeInformationEntity) {
        _cafeCurrentChecked.value = cafe
    }

    fun addExposedMarker(marker: Marker) {
        markerList.add(marker)
        _exposedMarker.value = markerList
    }

    fun clearExposedMarker() {
        _exposedMarker.value?.clear()
    }

    fun addCafeInsideCurrentCamera(key: CafeInformationEntity, isSelected: Boolean) {
        cafeList.forEach {
            cafeList[it.key] = false
        }
        cafeList[key] = isSelected
        _cafeInsideCurrentCamera.value = cafeList
    }

    fun removeAllCafeCurrentCamera() {
        cafeList.clear()
        _cafeInsideCurrentCamera.value = cafeList
    }

    //TagFilter
    private val checkedTagFilterCheckbox = ArrayList<CompoundButton>()
    private val _filterChecked = MutableLiveData<ArrayList<CompoundButton>>()
    val filterChecked: LiveData<ArrayList<CompoundButton>>
        get() = _filterChecked

    val taglist = arrayListOf<TagFilterEntity>()
    private val _checkedTagList = MutableLiveData<ArrayList<TagFilterEntity>>()
    val checkedTagList: LiveData<ArrayList<TagFilterEntity>>
        get() = _checkedTagList

    private val _countCafeResult = MutableLiveData<Int?>()
    val countCafeResult: LiveData<Int?>
        get() = _countCafeResult

    fun addFilterChecked(checkbox: CompoundButton) {
        checkedTagFilterCheckbox.add(checkbox)
        _filterChecked.value = checkedTagFilterCheckbox
    }

    fun removeFilterChecked(checkbox: CompoundButton) {
        checkedTagFilterCheckbox.remove(checkbox)
        _filterChecked.value = checkedTagFilterCheckbox
    }

    fun addTagList(tag: TagFilterEntity) {
        taglist.add(tag)
        _checkedTagList.value = taglist
    }

    fun updateCountCafeResult(count: Int?) {
        _countCafeResult.postValue(count)
    }

    fun removeTagList(tag: TagFilterEntity) {
        taglist.remove(tag)
        _checkedTagList.value = taglist
    }

    fun initializeFilterTag() {
        _filterChecked.value?.clear()
    }

    fun changeIsMyMap(myMap: Boolean) {
        _isMyMap.value = myMap
    }

    fun getCapinMapPins() {
        val tags = mutableListOf<Int?>()
        if (!taglist.isNullOrEmpty()) {
            taglist.forEach {
                tags.add(it.tagIndex)
            }
        }
        cafeListRepository.getCafeList(tags)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                cafeList.clear()
                changeCapinMapLocations(it)
                _countCafeResult.postValue(it.size)
            }, {
                it.printStackTrace()
            })
    }

    fun getMyMapPins() {
        val tags = mutableListOf<Int?>()
        if (!taglist.isNullOrEmpty()) {
            taglist.forEach {
                tags.add(it.tagIndex)
            }
        }
        myMapLocationsRepository.getPinCafes(tags)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                cafeList.clear()
                changeCapinMapLocations(it)
                _countCafeResult.postValue(it.size)
            }, {
                it.printStackTrace()
            })
    }

    fun changeCapinMapLocations(mapList: List<CafeInformationEntity>) {
        _capinMapCafeLocations.value = mapList
        val cafes = mutableMapOf<CafeInformationEntity, Boolean>()
        mapList.forEach { cafe ->
            cafes[cafe] = false
        }
        _cafeCurrentChecked.value = null
        _cafeInsideCurrentCamera.postValue(cafes)
    }

    fun getSelectedCafeDetailInfo() {
        _selectedCafe.value = UiState.loading(null)
        cafeInsideCurrentCamera.value?.forEach { isSelectedCafe ->
            if (isSelectedCafe.value) {
                cafeListRepository.getCafeDetail(isSelectedCafe.key.cafeId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        _selectedCafe.postValue(UiState.success(it))
                    }, {
                        _selectedCafe.postValue(UiState.error(null, it.message))
                        it.printStackTrace()
                    })
            }
        }
    }
}