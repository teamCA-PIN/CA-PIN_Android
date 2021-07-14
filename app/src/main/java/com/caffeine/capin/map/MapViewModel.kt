package com.caffeine.capin.map

import android.util.Log
import android.widget.CompoundButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.caffeine.capin.map.entity.CafeDetailEntity
import com.caffeine.capin.map.entity.CafeInformationEntity
import com.caffeine.capin.map.repository.CafeListRepository
import com.caffeine.capin.tagfilter.TagFilterEntity
import com.caffeine.capin.tagfilter.TagFilterList
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

    private val _tagList = MutableLiveData<ArrayList<TagFilterEntity?>>()
    val tagList: LiveData<ArrayList<TagFilterEntity?>>
        get() = _tagList

    fun changeTagList(selectedTagList: ArrayList<TagFilterEntity?>) {
        _tagList.value = selectedTagList
    }

    fun changeCafeCurrentChecked(cafe: CafeInformationEntity) {
        _cafeCurrentChecked.value = cafe
    }

    fun changeSelectedCafe(cafe: CafeDetailEntity) {
        _selectedCafe.value = cafe
    }

    init {
        switchToCapinMap()
    }

    private val _capinMapCafeLocations = MutableLiveData<List<CafeInformationEntity>>()
    val capinMapCafeLocation: LiveData<List<CafeInformationEntity>>
        get() = _capinMapCafeLocations

    fun changeCapinMapLocations(mapList: List<CafeInformationEntity>) {
        _capinMapCafeLocations.value = mapList
        mapList.forEach { cafe ->
            cafeList[cafe] = false
        }
        _cafeInsideCurrentCamera.postValue(cafeList)
    }

    fun unselectAllLocation() {
        cafeList.forEach{ cafe ->
            cafeList[cafe.key] = false
        }
        _cafeInsideCurrentCamera.value = cafeList
    }

//    fun getCapinMapCafeLocations() {
//        cafeListRepository.getCafeList(null,null,null,null,null,null)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({ cafes ->
//                _capinMapCafeLocations.postValue(cafes)
//                cafes.forEach { cafe ->
//                    cafeList[cafe] = false
//                }
//                _cafeInsideCurrentCamera.postValue(cafeList)
//            }, {
//                it.printStackTrace()
//            })
//    }

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
        initializeCapinMap()
//        getCafeLocations()
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

    private val taglist = arrayListOf<TagFilterEntity?>(null,null,null,null,null,null)
    private val _checkedTagList = MutableLiveData<ArrayList<TagFilterEntity?>>()
    val checkedTagList: LiveData<ArrayList<TagFilterEntity?>>
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
        taglist[tag.tagIndex] = tag
        _checkedTagList.value = taglist
    }

    fun updateCountCafeResult(count: Int?) {
        _countCafeResult.postValue(count)
    }

    fun removeTagList(tag: TagFilterEntity) {
        taglist[tag.tagIndex] = null
        _checkedTagList.value = taglist
    }

    fun getCafeLocations() {
        cafeListRepository.getCafeList(
            taglist[0]?.tagIndex,
            taglist[1]?.tagIndex,
            taglist[2]?.tagIndex,
            taglist[3]?.tagIndex,
            taglist[4]?.tagIndex,
            taglist[5]?.tagIndex
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                cafeList.clear()
                it.forEach { cafe ->
                    cafeList[cafe] = false
                }
                _cafeInsideCurrentCamera.postValue(cafeList)
                _countCafeResult.postValue(it.size)
            }, {
                it.printStackTrace()
            })
    }

    fun initializeCapinMap() {
        cafeListRepository.getCafeList(
            null,
            null,
            null,
            null,
            null,
            null
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                cafeList.clear()
                it.forEach { cafe ->
                    cafeList[cafe] = false
                }
                _cafeInsideCurrentCamera.postValue(cafeList)
                _countCafeResult.postValue(it.size)
            }, {
                it.printStackTrace()
            })
    }

    companion object {
        private val myMapInfo: List<CafeInformationEntity> = listOf(
            CafeInformationEntity("후엘고", 37.580221, 126.923442),
            CafeInformationEntity("빈플루", 37.582996109622876, 126.91380431146156)
        )
    }
}