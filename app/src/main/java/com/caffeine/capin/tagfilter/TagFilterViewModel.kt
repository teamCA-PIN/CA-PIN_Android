package com.caffeine.capin.tagfilter

import android.util.Log
import android.widget.CompoundButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.caffeine.capin.map.entity.CafeInformationEntity
import com.caffeine.capin.map.repository.CafeListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class TagFilterViewModel @Inject constructor(
    private val cafeListRepository: CafeListRepository
):ViewModel() {
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

    private val _tagResult = MutableLiveData<List<CafeInformationEntity>>()
    val tagResult: LiveData<List<CafeInformationEntity>>
        get() = _tagResult


    fun updateCountCafeResult(count: Int?) {
        _countCafeResult.postValue(count)
    }

    fun addTagList(tag: TagFilterEntity) {
        taglist[tag.tagIndex] = tag
        _checkedTagList.value = taglist
    }

    fun removeTagList(tag: TagFilterEntity) {
        taglist[tag.tagIndex] = null
        _checkedTagList.value = taglist
    }

    fun addFilterChecked(checkbox: CompoundButton) {
        checkedTagFilterCheckbox.add(checkbox)
        _filterChecked.value = checkedTagFilterCheckbox
    }

    fun removeFilterChecked(checkbox: CompoundButton) {
        checkedTagFilterCheckbox.remove(checkbox)
        _filterChecked.value = checkedTagFilterCheckbox
    }

    fun getCafeSize() {
        cafeListRepository.getCafeList(
            checkedTagList.value?.get(0)?.tagIndex,
            checkedTagList.value?.get(1)?.tagIndex,
            checkedTagList.value?.get(2)?.tagIndex,
            checkedTagList.value?.get(3)?.tagIndex,
            checkedTagList.value?.get(4)?.tagIndex,
            checkedTagList.value?.get(5)?.tagIndex
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _tagResult.postValue(it)
                _countCafeResult.postValue(it.size)
            }, {
                it.printStackTrace()
            })
    }

}