package com.caffeine.capin.review.write

import android.widget.CompoundButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.caffeine.capin.PictureUriEntity

class WriteReviewViewModel: ViewModel() {
    val rateOfReview = MutableLiveData<Float>(0.0f)
    val contentsOfReview = MutableLiveData<String>()

    private val cafePhotos = mutableListOf<PictureUriEntity>()

    private val _imagesOfCafe = MutableLiveData<List<PictureUriEntity>>()
    val imagesOfCafe: LiveData<List<PictureUriEntity>>
        get() = _imagesOfCafe

    private val _recommendation = MutableLiveData<List<Int?>>()
    val recommendation: LiveData<List<Int?>>
        get() = _recommendation

    private val _checkedRecommend = MutableLiveData<Map<CompoundButton, Int>>()
    val checkedRecommend: LiveData<Map<CompoundButton, Int>>
        get() = _checkedRecommend

    fun changeCheckedRecommend(map: Map<CompoundButton, Int>) {
        _checkedRecommend.value = map
    }


    fun addImagesOfCafe(pictureUriEntity: PictureUriEntity) {
        cafePhotos.add(pictureUriEntity)
        _imagesOfCafe.value = cafePhotos
    }

    fun deleteImageOfCafe(pictureUriEntity: PictureUriEntity) {
        cafePhotos.remove(pictureUriEntity)
        _imagesOfCafe.value = cafePhotos
    }


    fun checkImagesCount(): Boolean {
        return if (imagesOfCafe.value.isNullOrEmpty()) {
            true
        } else {
            imagesOfCafe.value!!.size < 5
        }
    }

    fun switchPostButtonActivation(): Boolean {
        return rateOfReview.value != 0.0f && !(contentsOfReview.value.isNullOrBlank())
    }

    fun uploadReview() {

    }
}