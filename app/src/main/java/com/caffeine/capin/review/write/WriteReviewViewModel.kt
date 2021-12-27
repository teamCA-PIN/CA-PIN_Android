package com.caffeine.capin.review.write

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.caffeine.capin.review.write.controller.WriteReviewController
import com.caffeine.capin.util.FormDataUtil
import com.caffeine.capin.util.FormDataUtil.asMultipart
import com.caffeine.capin.util.FormDataUtil.convertToRequestBody
import com.caffeine.capin.util.JsonStringParser
import com.caffeine.capin.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.MultipartBody
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class WriteReviewViewModel @Inject constructor(
    private val writeReviewController: WriteReviewController

) : ViewModel() {
    val rateOfReview = MutableLiveData<Float>(0.0f)
    val contentsOfReview = MutableLiveData<String>()

    private val cafePhotos = mutableListOf<PictureUriEntity>()

    private val _imagesOfCafe = MutableLiveData<List<PictureUriEntity>>()
    val imagesOfCafe: LiveData<List<PictureUriEntity>>
        get() = _imagesOfCafe

    private val _cafeId = MutableLiveData<String>()
    val cafeId: LiveData<String>
        get() = _cafeId

    private val _initialImageCount = MutableLiveData<Int>()
    val initialImageCount: LiveData<Int>
        get() = _initialImageCount

    private val _recommendation = MutableLiveData(mutableListOf<Int?>())
    val recommendation: LiveData<MutableList<Int?>>
        get() = _recommendation

    private val _successPost = MutableLiveData<UiState<Boolean>>()
    val successPost: LiveData<UiState<Boolean>>
        get() = _successPost

    private val _checkedRecommend = MutableLiveData<List<Int?>>()
    val checkedRecommend: LiveData<List<Int?>>
        get() = _checkedRecommend

    private val _reviewId = MutableLiveData<String>()
    val reviewId: LiveData<String>
        get() = _reviewId

    fun changeCafeId(cafeId: String) {
        _cafeId.value = cafeId
    }

    fun changeReviewId(id: String) {
        _reviewId.value = id
    }

    fun changeInitialImageCounts(count: Int) {
        _initialImageCount.value = count
    }

    fun changeCheckedRecommend(list: List<Int?>) {
        _checkedRecommend.value = list
    }

    fun changeImageOfCafe(pictures: List<PictureUriEntity>) {
        _imagesOfCafe.value = pictures
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
        checkedRecommend.value?.forEach {
            _recommendation.value?.add(it)
        }
        val review = RequestWriteReview(
            _recommendation.value,
            contentsOfReview.value!!,
            rateOfReview.value!!,
            null
        )
        val reviewJson = FormDataUtil.getBody("review", JsonStringParser.parseToJsonString(review))

        imagesOfCafe.value?.forEach { picture ->
            requestMap.add(picture.uri?.asMultipart("imgs", contentResolver))
        }

        _successPost.value = UiState.loading(null)
        writeReviewController.postReview(
            cafeId.value!!,
            reviewJson,
            requestMap
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _successPost.postValue(UiState.success(null))

            }, {
                _successPost.postValue(UiState.error(null, it.toString()))
                it.printStackTrace()
            })
    }

    fun modifyReview(contentResolver: ContentResolver) {
        var requestMap = mutableListOf<MultipartBody.Part?>()

        checkedRecommend.value?.forEach {
            _recommendation.value?.add(it)
        }
        val review = RequestWriteReview(
            _recommendation.value,
            contentsOfReview.value!!,
            rateOfReview.value!!,
            imagesOfCafe.value?.count() == 0 || imagesOfCafe.value == null
        )

        val reviewJson = FormDataUtil.getBody("review", JsonStringParser.parseToJsonString(review))

        imagesOfCafe.value?.let {
            if(it.count() != initialImageCount.value || it.all { it.uri != null }) {
                it.forEach { picture ->
                    requestMap.add(picture.uri?.asMultipart("imgs", contentResolver))
                    picture.url?.let {
                        requestMap.add(convertUrlToMultipart(it))
                    }
                }
            }
        }

        Log.e("requestMap", "$requestMap")
        _successPost.value = UiState.loading(null)
        writeReviewController.modifyReview(
            reviewId.value ?: "",
            reviewJson,
            requestMap
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _successPost.postValue(UiState.success(null))
            }, {
                _successPost.postValue(UiState.error(null, it.toString()))
                it.printStackTrace()
            })
    }

    private fun convertUrlToMultipart(url: String): MultipartBody.Part? {
        val bitmap = Single.fromCallable { convertBitmapFromUrl(url)  }
            .subscribeOn(Schedulers.io()).blockingGet()

        return bitmap?.let {
            MultipartBody.Part.createFormData("imgs", "imgs", it.convertToRequestBody())
        }
    }

    private fun convertBitmapFromUrl(url: String): Bitmap? {
        try {
            val url = URL(url)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            return BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

}