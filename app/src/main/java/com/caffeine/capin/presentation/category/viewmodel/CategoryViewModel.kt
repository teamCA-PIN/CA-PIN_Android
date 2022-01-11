package com.caffeine.capin.presentation.category.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.caffeine.capin.data.controller.archive.ArchiveCafeController
import com.caffeine.capin.domain.entity.category.CategoryNameEntity
import com.caffeine.capin.data.dto.category.RequestArchiveCafeDTO
import com.caffeine.capin.domain.repository.category.CategoryListRepository
import com.caffeine.capin.domain.entity.map.CafeDetailEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryListRepository: CategoryListRepository,
    private val archiveCafeController: ArchiveCafeController
): ViewModel() {
    private val _cafeName = MutableLiveData<String>()
    val cafeName: LiveData<String>
        get() = _cafeName

    private val _selectedCafeDetail = MutableLiveData<CafeDetailEntity>()
    val selectedCafeDetail: LiveData<CafeDetailEntity>
        get() = _selectedCafeDetail

    private val _categoryList = MutableLiveData<List<CategoryNameEntity>>()
    val categoryList: LiveData<List<CategoryNameEntity>>
        get() = _categoryList

    private val _selectedCategory = MutableLiveData<CategoryNameEntity>()
    val selectedCategory: LiveData<CategoryNameEntity>
        get() = _selectedCategory

    private val _isSuccessArchive = MutableLiveData<Boolean>()
    val isSuccessArchive: LiveData<Boolean>
        get() = _isSuccessArchive

    fun changeCafeName(cafeName: String) {
        _cafeName.value = cafeName
    }

    fun changeSelectedCafeDetail(cafe: CafeDetailEntity) {
        _selectedCafeDetail.value = cafe
    }

    init {
        getCategoryList()
    }

    fun changeCategoryList(categotries: List<CategoryNameEntity>) {
        _categoryList.value = categotries
    }

    fun changeSelectedCategory(category: CategoryNameEntity) {
        _selectedCategory.value = category
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

    fun archiveCafe() {
        archiveCafeController.archiveCafe(
            selectedCafeDetail.value!!._id,
            RequestArchiveCafeDTO(
                selectedCategory.value?.id
            )
        ).observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _isSuccessArchive.postValue(true)
            }, {
                _isSuccessArchive.postValue(false)

            })
    }


}