package com.caffeine.capin.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CategoryViewModel: ViewModel() {
    private val _cafeName = MutableLiveData<String>()
    val cafeName: LiveData<String>
        get() = _cafeName

    private val _categoryList = MutableLiveData<List<CategoryNameEntity>>()
    val categoryList: LiveData<List<CategoryNameEntity>>
        get() = _categoryList


    init {
        changeCategoryList(DUMMY_CATEGORY)
    }

    fun changeCafeName(cafeName: String) {
        _cafeName.value = cafeName
    }

    fun changeCategoryList(categotries: List<CategoryNameEntity>) {
        _categoryList.value = categotries
    }

    companion object {
        private val DUMMY_CATEGORY = listOf<CategoryNameEntity>(
            CategoryNameEntity(
                "6492f5",
                "기본 카테고리"
            ),
            CategoryNameEntity(
                "6bbc9a",
                "Peace List"
            ),
            CategoryNameEntity(
                "ffc24b",
                "호엥고"
            ),
            CategoryNameEntity(
                "816f7c",
                "상수동 카페 리스트"
            ),
            CategoryNameEntity(
                "ffc2d5",
                "핑핑이들"
            ),
            CategoryNameEntity(
                "c9d776",
                "Capin"
            )
        )
    }
}