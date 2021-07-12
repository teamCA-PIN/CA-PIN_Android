package com.caffeine.capin.review

import android.graphics.Picture
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WriteReviewViewModel: ViewModel() {
    val rateOfReview = MutableLiveData<Float>(0.0f)
    val contentsOfReview = MutableLiveData<String>()

    private val cafePhotos = mutableListOf<PictureUriEntity>()

    private val _imagesOfCafe = MutableLiveData<List<PictureUriEntity>>()
    val imagesOfCafe: LiveData<List<PictureUriEntity>>
        get() = _imagesOfCafe

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
}