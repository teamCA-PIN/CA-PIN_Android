package com.caffeine.capin.tagfilter

import android.widget.CompoundButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TagFilterViewModel:ViewModel() {
    private val _filterChecked = MutableLiveData<ArrayList<CompoundButton>>()
    val filterChecked: LiveData<ArrayList<CompoundButton>>
        get() = _filterChecked

    private val checkedTagFilter = ArrayList<CompoundButton>()

    fun addFilterChecked(checkbox: CompoundButton) {
        checkedTagFilter.add(checkbox)
        _filterChecked.value = checkedTagFilter
    }

    fun removeFilterChecked(checkbox: CompoundButton) {
        checkedTagFilter.remove(checkbox)
        _filterChecked.value = checkedTagFilter
    }
}