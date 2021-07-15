package com.caffeine.capin.review.write

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import android.widget.CompoundButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.caffeine.capin.PictureUriEntity
import com.caffeine.capin.util.FormDataUtil
import com.caffeine.capin.util.FormDataUtil.asMultipart
import com.caffeine.capin.util.JsonStringParser
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink
import okio.source
import javax.inject.Inject

@HiltViewModel
class WriteReviewViewModel @Inject constructor(
    private val writeReviewController: WriteReviewController

): ViewModel() {
    val rateOfReview = MutableLiveData<Float>(0.0f)
    val contentsOfReview = MutableLiveData<String>()

    private val cafePhotos = mutableListOf<PictureUriEntity>()

    private val _imagesOfCafe = MutableLiveData<List<PictureUriEntity>>()
    val imagesOfCafe: LiveData<List<PictureUriEntity>>
        get() = _imagesOfCafe

    private val _recommendation = MutableLiveData<MutableList<Int?>>()
    val recommendation: LiveData<MutableList<Int?>>
        get() = _recommendation

    private val _successPost = MutableLiveData<Boolean>()
    val successPost: LiveData<Boolean>
        get() = _successPost

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

    fun uploadReview(contentResolver: ContentResolver) {
        var requestMap = mutableListOf<MultipartBody.Part?>()

        checkedRecommend.value?.forEach{
            if (it.key.isChecked) _recommendation.value?.add(it.value)
        }

        val review = RequestWriteReview(
            _recommendation.value,
            contentsOfReview.value!!,
            rateOfReview.value!!
        )
        val reviewJson = FormDataUtil.getBody("review", JsonStringParser.parseToJsonString(review))

        imagesOfCafe.value?.forEach { picture ->
            requestMap.add(picture.uri?.asMultipart("imgs",contentResolver))
        }

        //Todo: 플로우 연결 후 카페 아이디 하드코딩 수정

       writeReviewController.postReview(
            "60e96789868b7d75f394b06a",
            reviewJson,
            requestMap
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _successPost.postValue(true)
            }, {
                it.printStackTrace()
                _successPost.postValue(false)
            })
    }

}