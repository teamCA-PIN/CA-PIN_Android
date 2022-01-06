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
import com.naver.maps.geometry.LatLng
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
    private val _isMyMap = MutableLiveData<Boolean>()
    val isMyMap: LiveData<Boolean>
        get() = _isMyMap

    private val _exposedMarker = MutableLiveData<MutableList<Marker>>()
    val exposedMarker: LiveData<MutableList<Marker>>
        get() = _exposedMarker

    private val _cafeInsideCurrentCamera = MutableLiveData<List<CafeInformationEntity>>()
    val cafeInsideCurrentCamera: LiveData<List<CafeInformationEntity>>
        get() = _cafeInsideCurrentCamera


    private val _selectedCafeDetail = MutableLiveData<UiState<CafeDetailEntity>>()
    val selectedCafeDetail: LiveData<UiState<CafeDetailEntity>>
        get() = _selectedCafeDetail

    private val _cafeCurrentChecked = MutableLiveData<CafeInformationEntity?>()
    val cafeCurrentChecked: LiveData<CafeInformationEntity?>
        get() = _cafeCurrentChecked

    private val _cafeLocations = MutableLiveData<List<CafeInformationEntity>>()
    val cafeLocations: LiveData<List<CafeInformationEntity>>
        get() = _cafeLocations

    init {
        if(isMyMap.value == true) {
            getMyMapPins()
        } else {
            getCapinMapPins()
        }
    }

    fun changeCafeCurrentChecked(cafe: CafeInformationEntity?) {
        _cafeCurrentChecked.value = cafe
    }

    fun changeExposedMarkers(markers: List<Marker>) {
        _exposedMarker.value = markers.toMutableList()
    }

    fun clearExposedMarker() {
        _exposedMarker.value?.clear()
    }

    fun clearCheckedCafe() {
        _cafeCurrentChecked.value = null
    }

    fun changeCafeInsideCurrentCamera(cafes: List<CafeInformationEntity>) {
        _cafeInsideCurrentCamera.value = cafes
    }

    fun removeAllCafeCurrentCamera() {
        val cafes = cafeInsideCurrentCamera.value?.toMutableList() ?: mutableListOf()
        cafes.clear()
        _cafeInsideCurrentCamera.value = cafes
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
        if (!taglist.isNullOrEmpty()) { taglist.forEach { tags.add(it.tagIndex) } }
        cafeListRepository.getCafeList(tags)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                changeCapinMapLocations(it)
                _countCafeResult.postValue(it.size)
            }, {
                _countCafeResult.postValue(0)
                it.printStackTrace()
            })
    }

    fun getMyMapPins() {
        val tags = mutableListOf<Int?>()
        if (!taglist.isNullOrEmpty()) { taglist.forEach { tags.add(it.tagIndex) } }
        myMapLocationsRepository.getPinCafes(tags)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                changeCapinMapLocations(it)
                _countCafeResult.postValue(it.size)
            }, {
                changeCapinMapLocations(listOf())
                _countCafeResult.postValue(0)
                it.printStackTrace()
            })
    }

    private fun changeCapinMapLocations(mapList: List<CafeInformationEntity>) {
        _cafeLocations.postValue(mapList)
        _cafeCurrentChecked.postValue(null)
        _selectedCafeDetail.postValue(UiState.error(null, null))
    }

    fun getSelectedCafeDetailInfo() {
        _selectedCafeDetail.value = UiState.loading(null)
        cafeCurrentChecked.value?.let {
            cafeListRepository.getCafeDetail(it.cafeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _selectedCafeDetail.postValue(UiState.success(it))
                }, {
                    _selectedCafeDetail.postValue(UiState.error(null, it.message))
                    it.printStackTrace()
                })
        }
    }
}