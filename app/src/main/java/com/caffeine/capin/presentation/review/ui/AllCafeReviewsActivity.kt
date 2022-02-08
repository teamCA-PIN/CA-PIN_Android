package com.caffeine.capin.presentation.review.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.caffeine.capin.R
import com.caffeine.capin.data.dto.mypage.MyReview
import com.caffeine.capin.data.dto.review.CafeReview
import com.caffeine.capin.data.local.UserPreferenceManager
import com.caffeine.capin.databinding.ActivityAllCafeReviewsBinding
import com.caffeine.capin.presentation.customview.*
import com.caffeine.capin.presentation.mypage.MyReviewImageDialog
import com.caffeine.capin.presentation.review.viewmodel.AllCafeReviewsViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AllCafeReviewsActivity : AppCompatActivity() {
    @Inject lateinit var userPreferenceManager: UserPreferenceManager
    private lateinit var binding: ActivityAllCafeReviewsBinding
    private val allCafeReviewsViewModel: AllCafeReviewsViewModel by viewModels()
    private val cafeReviewsAdapter = CafeReviewsAdapter(object: CafeReviewsAdapter.ExpandImageInterface{
        override fun expand(images: List<String>) {
            MyReviewImageDialog(images as ArrayList<String>).show(supportFragmentManager, "SampleDialog")
        }
        override fun edit(review: CafeReview) {
            with(userPreferenceManager) {
                if (getUserNickName() == review.writerNickname) {
                    showEditReviewDialog(review)
                } else {
                    showReportReviewDialog(review)
                }
            }
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAllCafeReviewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.run {
            toolbar.setToolbarTitle("리뷰 전체보기")
            toolbar.setBackButton{
                finish()
            }
        }

        binding.lifecycleOwner = this
        binding.viewModel = allCafeReviewsViewModel
        binding.listReviews.adapter = cafeReviewsAdapter

        allCafeReviewsViewModel.cafeReviews.observe(this) {
            cafeReviewsAdapter.submitList(it)
        }
        showResultToast()
        allCafeReviewsViewModel.loadCafeDetails(getCafeId())
    }

    private fun getCafeId(): String {
        return intent.getStringExtra(KEY_CAFE_ID) ?: error("cafe id must be put in intent")
    }

    private fun showReportReviewDialog(cafeReview: CafeReview) {
        val reportDialog = CapinDialogBuilder(null)
            .setContentDialogTitile("리뷰를 신고하시겠습니까?")
            .setContent("카페 후기와 관계 없거나, 무의미한 내용, 욕설 등이\n포함될 경우 검토 후 삭제됩니다.")
            .setContentDialogButtons(true, "취소", "신고", object: DialogClickListener {
                override fun onClick() {
                    CustomToastBuilder(this@AllCafeReviewsActivity, "리뷰가 신고되었습니다.", binding.constraintlayoutRoot).build()
                    allCafeReviewsViewModel.reportReview((cafeReview.reviewId))
                }
            })
            .build()
        reportDialog.show(supportFragmentManager, "report dialog")
    }

    private fun showEditReviewDialog(review: CafeReview) {
        val buttonList = ArrayList<CapinDialogButton>()
        val dialog: CapinDialog = CapinDialogBuilder("리뷰 편집")
            .setButtonArray(buttonList)
            .setExitButton(true)
            .build()

        buttonList.apply {
            add(
                createMyReviewDialogButton("리뷰 수정", R.color.maincolor_1) {
                    goToModifyReview(review)
                    dialog.dismiss() }
            )
            add(
                createMyReviewDialogButton("리뷰 삭제", R.color.pointcolor_red) {
                    allCafeReviewsViewModel.deleteReview(review.reviewId)
                    dialog.dismiss()
                    allCafeReviewsViewModel.removeReviewData(review)
                })
        }

        dialog.show(supportFragmentManager, "EditReview")
    }

    private fun createMyReviewDialogButton(text: String, color: Int, listener: () -> Unit): CapinDialogButton {
        return CapinDialogButton(text, ContextCompat.getColor(this, color), this, object: CapinDialogButton.OnClickListener{
            override fun onClick() {
                listener()
            }
        } )
    }

    private fun goToModifyReview(cafeReview: CafeReview) {
        val intent = Intent(this, WriteReviewActivity::class.java)
        val gson = Gson()
        val review = cafeReview.run {
            MyReview(writerId, cafeId, intent.getStringExtra(CAFE_NAME)?: "", content, createDate.toString(), imageUrls, starRate, recommend.map { it.id })
        }
        val reviewSelected = gson.toJson(review)
        intent.putExtra(WriteReviewActivity.EDIT_REVIEW, reviewSelected)
        startActivity(intent)
    }


    private fun showResultToast() {
        allCafeReviewsViewModel.isDeleteSuccess.observe(this) { success ->
            CustomToastBuilder(this, if (success) "리뷰가 삭제되었습니다." else "리뷰를 삭제하지 못하였습니다.", binding.constraintlayoutRoot).build()
        }
    }

    companion object {
        const val KEY_CAFE_ID = "KEY_CAFE_ID"
        const val CAFE_NAME = "CAFE_NAME"
    }
}
