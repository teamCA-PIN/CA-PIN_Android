package com.caffeine.capin.detail

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caffeine.capin.network.CapinApiService
import com.caffeine.capin.review.CafeReview
import com.caffeine.capin.review.Recommend
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

/**
 * Created By Malibin
 * on 7월 09, 2021
 */

@HiltViewModel
class CafeDetailsViewModel @Inject constructor(
    private val capinService: CapinApiService,
) : ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _cafeDetails = MutableLiveData<CafeDetails>()
    val cafeDetails: LiveData<CafeDetails> = _cafeDetails

    fun loadCafeDetails(cafeId: String) {
        _isLoading.value = true
        capinService.getCafeDetail(cafeId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onSuccessLoadDetails(it.toCafeDetails()) },
                ::onServiceFailure,
            )
    }

    private fun onSuccessLoadDetails(details: CafeDetails) {
        _cafeDetails.value = details
        _isLoading.value = false
    }

    private fun onServiceFailure(t: Throwable) {
        Log.d("MalibinDebug", TextUtils.join("\n", t.stackTrace))
        _isLoading.value = false
    }

    private fun getMockCafeDetails(): CafeDetails = CafeDetails(
        id = "id",
        cafeName = "후엘고",
        mainImageUrl = "https://s3-ap-northeast-1.amazonaws.com/dcreviewsresized/300_300_20210125203545_photo3_3bcd18e14f3d.jpg",
        starRate = 4.5,
        address = "서울 마포구 마포대로 11길 118 1층 (염리동)",
        tags = listOf("커피맛집", "디저트맛집", "그루비", "작업하기좋은", "조용한", "친절왕", "채광좋은"),
        instagramId = "@huelgocoffee",
        operationTime = "평일 11:00~22:00 (일요일 휴무)\n공휴일 12:00~20:00",
    )

    private fun getStubReviews(): List<CafeReview> = listOf(
        CafeReview(
            id = "1",
            profileImageUrl = "http://file3.instiz.net/data/cached_img/upload/2021/01/27/18/2ce2f41f7e9f09c0acc77faac7452dbf.jpg",
            writerNickname = "슬픈 루피",
            createDate = Date(),
            recommend = listOf(Recommend.TASTE, Recommend.MOOD),
            content = "무엇보다 커피가 정말 맛있고, 디저트로 준비돼있던 쿠키와 휘낭시에도 맛있었습니다. 브라운크림은 꼭 드세요 !",
            starRate = 3.5,
            type = "F",
            isMine = false,
            imageUrls = listOf(
                "http://cafecurator.com/wp-content/uploads/2018/05/P20180523_105406842_1A1F2DEF-80CA-4955-8F72-D512751523D9.jpg",
                "https://img1.daumcdn.net/thumb/R720x0.q80/?scode=mtistory2&fname=http%3A%2F%2Fcfile21.uf.tistory.com%2Fimage%2F99ED1E3D5B951DD701B60F",
                "https://bwissue.com/files/attach/images/243/501/143/001/240162aff7762c99776e7c6584db7c9c.jpg",
                "https://s3-ap-northeast-1.amazonaws.com/dcreviewsresized/300_300_20210125203545_photo2_3bcd18e14f3d.jpg",
                "https://t1.daumcdn.net/cfile/tistory/99F8293C5BC9DA6F20",
            ),
        ),
        CafeReview(
            id = "2",
            profileImageUrl = "http://file3.instiz.net/data/cached_img/upload/2021/01/27/18/2ce2f41f7e9f09c0acc77faac7452dbf.jpg",
            writerNickname = "슬픈 루피",
            createDate = Date(),
            recommend = listOf(Recommend.MOOD),
            content = "무엇보다 커피가 정말 맛있고, 디저트로 준비돼있던 쿠키와 휘낭시에도 맛있었습니다. 브라운크림은 꼭 드세요 ! 무엇보다 커피가 정말 맛있고, 디저트로 준비돼있던 쿠키와 휘낭시에도 맛있었습니다. 브라운크림은 꼭 드세요 ! 무엇보다 커피가 정말 맛있고, 디저트로 준비돼있던 쿠키와 휘낭시에도 맛있었습니다. 브라운크림은 꼭 드세요 !",
            starRate = 3.5,
            type = "F",
            isMine = false,
            imageUrls = listOf(
                "http://cafecurator.com/wp-content/uploads/2018/05/P20180523_105406842_1A1F2DEF-80CA-4955-8F72-D512751523D9.jpg",
                "https://img1.daumcdn.net/thumb/R720x0.q80/?scode=mtistory2&fname=http%3A%2F%2Fcfile21.uf.tistory.com%2Fimage%2F99ED1E3D5B951DD701B60F",
                "https://bwissue.com/files/attach/images/243/501/143/001/240162aff7762c99776e7c6584db7c9c.jpg",
                "https://s3-ap-northeast-1.amazonaws.com/dcreviewsresized/300_300_20210125203545_photo2_3bcd18e14f3d.jpg",
            ),
        ),
        CafeReview(
            id = "3",
            profileImageUrl = "http://file3.instiz.net/data/cached_img/upload/2021/01/27/18/2ce2f41f7e9f09c0acc77faac7452dbf.jpg",
            writerNickname = "슬픈 루피",
            createDate = Date(),
            recommend = listOf(Recommend.TASTE),
            content = "무엇보다 커피가 정말 맛있다!",
            starRate = 3.5,
            type = "F",
            isMine = false,
            imageUrls = listOf(
                "http://cafecurator.com/wp-content/uploads/2018/05/P20180523_105406842_1A1F2DEF-80CA-4955-8F72-D512751523D9.jpg",
                "https://img1.daumcdn.net/thumb/R720x0.q80/?scode=mtistory2&fname=http%3A%2F%2Fcfile21.uf.tistory.com%2Fimage%2F99ED1E3D5B951DD701B60F",
            ),
        ),
        CafeReview(
            id = "3",
            profileImageUrl = "http://file3.instiz.net/data/cached_img/upload/2021/01/27/18/2ce2f41f7e9f09c0acc77faac7452dbf.jpg",
            writerNickname = "슬픈 루피",
            createDate = Date(),
            recommend = emptyList(),
            content = "무엇보다 커피가 정말 맛있다!",
            starRate = 3.5,
            type = "F",
            isMine = false,
            imageUrls = emptyList(),
        ),
    )
}
