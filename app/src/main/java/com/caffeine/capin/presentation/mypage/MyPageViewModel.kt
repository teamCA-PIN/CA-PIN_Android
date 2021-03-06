package com.caffeine.capin.presentation.mypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.caffeine.capin.data.dto.mypage.ResponseMyCategoryData
import com.caffeine.capin.data.dto.category.MyCategory
import com.caffeine.capin.data.network.BaseResponse
import com.caffeine.capin.data.network.ServiceCreator
import com.caffeine.capin.data.local.UserPreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val userPreferenceManager: UserPreferenceManager
) : ViewModel() {
    private val _categories = MutableLiveData<List<MyCategory>>()
    val categories: LiveData<List<MyCategory>>
        get() = _categories

    private var fakeCategories = mutableListOf<MyCategory>()

    private val _removeCategoryInfo = MutableLiveData<MyCategory>()
    val removeCategoryInfo: LiveData<MyCategory>
        get() = _removeCategoryInfo

    private val _isSuccessDeleteCategory = MutableLiveData<Boolean>()
    val isSuccessDeleteCategory: LiveData<Boolean>
        get() = _isSuccessDeleteCategory

    fun changeRemoveCategoryInfo(category: MyCategory) {
        _removeCategoryInfo.value = category
    }

    fun removeCategories() {
        fakeCategories.remove(removeCategoryInfo.value)
        _categories.value = fakeCategories
    }


    fun getMyCategoryFromServer() {
        val capinApiService = ServiceCreator.capinApiService.getMyCategory(
            userPreferenceManager.getUserAccessToken()
        )

        capinApiService.enqueue(object : Callback<ResponseMyCategoryData> {
            override fun onFailure(
                call: Call<ResponseMyCategoryData>,
                t: Throwable
            ) { //통신실패
                Log.d("fail", "error:$t")
            }

            override fun onResponse(
                call: Call<ResponseMyCategoryData>,
                response: Response<ResponseMyCategoryData>
            ) { //통신 성공
                if (response.isSuccessful) {
                    fakeCategories = response.body()?.myCategoryList as MutableList<MyCategory>
                    _categories.value = response.body()?.myCategoryList
                } else {
                    Log.e("error", "")
                }
            }
        })
    }


    fun deleteMyCategoryAtServer() {
        val capinApiService = ServiceCreator.capinApiService.deleteMyCategory(
            userPreferenceManager.getUserAccessToken(),
            removeCategoryInfo.value!!._id
        )

        capinApiService.enqueue(object : Callback<BaseResponse> {
            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.d("fail", "error:$t")
            }

            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                _isSuccessDeleteCategory.value = response.isSuccessful
            }
        })
    }

}
