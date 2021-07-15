package com.caffeine.capin.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryListRepository: CategoryListRepository
): ViewModel() {
    private val _cafeName = MutableLiveData<String>()
    val cafeName: LiveData<String>
        get() = _cafeName

    private val _categoryList = MutableLiveData<List<CategoryNameEntity>>()
    val categoryList: LiveData<List<CategoryNameEntity>>
        get() = _categoryList

    fun changeCafeName(cafeName: String) {
        _cafeName.value = cafeName
    }

    init {
        getCategoryList()
    }

    fun changeCategoryList(categotries: List<CategoryNameEntity>) {
        _categoryList.value = categotries
    }

    fun getCategoryList() {
        categoryListRepository.getCategoryList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                       _categoryList.postValue(it)
            }, {
                it.printStackTrace()
            })
    }

}