package com.caffeine.capin.presentation.cafedetail.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.caffeine.capin.R
import com.caffeine.capin.data.dto.mypage.MyReview
import com.caffeine.capin.data.dto.review.CafeReview
import com.caffeine.capin.data.local.UserPreferenceManager
import com.caffeine.capin.databinding.ActivityCafeDetailsBinding
import com.caffeine.capin.presentation.cafedetail.viewmodel.CafeDetailsViewModel
import com.caffeine.capin.presentation.category.ui.SelectCategoryActivity
import com.caffeine.capin.presentation.customview.*
import com.caffeine.capin.presentation.map.MapFragment
import com.caffeine.capin.presentation.mypage.MyReviewImageDialog
import com.caffeine.capin.presentation.review.ui.AllCafeReviewsActivity
import com.caffeine.capin.presentation.review.ui.CafeReviewsAdapter
import com.caffeine.capin.presentation.review.ui.ReviewTagAdapter
import com.caffeine.capin.presentation.review.ui.WriteReviewActivity
import com.caffeine.capin.presentation.review.ui.WriteReviewActivity.Companion.EDIT_REVIEW
import com.caffeine.capin.presentation.util.HorizontalItemDecoration
import com.caffeine.capin.util.copyToClipBoard
import com.google.android.flexbox.*
import com.google.android.material.appbar.AppBarLayout
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CafeDetailsActivity: AppCompatActivity() {
    @Inject lateinit var userPreferenceManager: UserPreferenceManager
    private lateinit var binding: ActivityCafeDetailsBinding
    private val cafeDetailsViewModel: CafeDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCafeDetailsBinding.inflate(layoutInflater)
        binding.viewModel = cafeDetailsViewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)

        setReviewAdapter()
        loadCafeTags()
        checkToolbarCollapsed()
        copyInstagramId()
        checkIsReviewWritten()
        showResultToast()

        binding.buttonMenus.setOnClickListener { deployMenusActivity() }
        binding.imageviewBack.setOnClickListener { finish() }
        binding.buttonAllReviews.setOnClickListener { deployAllCafeReviewsActivity() }
        binding.buttonWriteReview.setOnClickListener { deployWriteReviewActivity() }
        binding.layoutSavePinButton.root.setOnClickListener { deploySelectCategoryActivity() }
    }

    override fun onResume() {
        super.onResume()
        cafeDetailsViewModel.loadCafeDetails(getCafeId())
    }

    private fun checkIsReviewWritten() {
        intent.getStringExtra(EDIT_REVIEW)?.let { cafeDetailsViewModel.updateReviewWritten(it) }
    }

    private fun setReviewAdapter() {
        val adapter = CafeReviewsAdapter(object: CafeReviewsAdapter.ExpandImageInterface{
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
        binding.recyclerviewReview.adapter = adapter
        cafeDetailsViewModel.cafeReviews.observe(this) { adapter.submitList(it) }
    }

    private fun checkToolbarCollapsed() {
        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if ((binding.collapsingToolbar.height + verticalOffset) < (2 * ViewCompat.getMinimumHeight(binding.collapsingToolbar))) {
                binding.imageviewBack.setImageResource(R.drawable.icon_back_black)
            } else {
                binding.imageviewBack.setImageResource(R.drawable.icon_back_white)
            }
        })
    }

    private fun loadCafeTags() {
        binding.recyclerviewCafeTags.run {
            this.adapter = ReviewTagAdapter(1)
            layoutManager = FlexboxLayoutManager(this@CafeDetailsActivity).apply {
                addItemDecoration(HorizontalItemDecoration(4))
                flexWrap = FlexWrap.WRAP
                flexDirection = FlexDirection.ROW
                justifyContent = JustifyContent.CENTER
                alignItems = AlignItems.CENTER

            }
            cafeDetailsViewModel.cafeDetailsEntity.observe(this@CafeDetailsActivity) {
                (this.adapter as ReviewTagAdapter).submitList(it.tags)
            }
        }
    }

    private fun showReportReviewDialog(cafeReview: CafeReview) {
        val reportDialog = CapinDialogBuilder(null)
            .setContentDialogTitile("리뷰를 신고하시겠습니까?")
            .setContent("카페 후기와 관계 없거나, 무의미한 내용, 욕설 등이\n포함될 경우 검토 후 삭제됩니다.")
            .setContentDialogButtons(true, "취소", "신고", object: DialogClickListener {
                override fun onClick() {
                    CustomToastBuilder(this@CafeDetailsActivity, "리뷰가 신고되었습니다.", binding.constraintlayoutRoot).build()
                    cafeDetailsViewModel.reportReview((cafeReview.reviewId))
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
                    dialog.dismiss()
                }
            )
            add(
                createMyReviewDialogButton("리뷰 삭제", R.color.pointcolor_red) {
                    cafeDetailsViewModel.deleteReview(review.reviewId)
                    dialog.dismiss()
                    cafeDetailsViewModel.removeReviewData(review)
                }
            )
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

    private fun getCafeId(): String {
        return intent.getStringExtra(KEY_CAFE_ID) ?: error("cafe id must be put in intent")
    }

    private fun deployMenusActivity() {
        Intent(this, CafeMenusActivity::class.java)
            .apply { putExtra(CafeMenusActivity.KEY_CAFE_ID, getCafeId()) }
            .also { startActivity(it) }
    }

    private fun deployAllCafeReviewsActivity() {
        Intent(this, AllCafeReviewsActivity::class.java)
            .apply {
                putExtra(AllCafeReviewsActivity.CAFE_NAME, cafeDetailsViewModel.cafeDetailsEntity.value?.cafeName)
                putExtra(AllCafeReviewsActivity.KEY_CAFE_ID, getCafeId())
            }
            .also { startActivity(it) }
    }

    private fun deployWriteReviewActivity() {
        Intent(this, WriteReviewActivity::class.java)
            .apply {
                cafeDetailsViewModel.isReviewWritten.value?.let {
                    if (it.isNotEmpty()) {
                        putExtra(EDIT_REVIEW, it)
                    } else {
                        putExtra(WriteReviewActivity.KEY_CAFE_ID, getCafeId())
                    }
                } ?: putExtra(WriteReviewActivity.KEY_CAFE_ID, getCafeId())
            }
            .also { startActivity(it) }
    }

    private fun deploySelectCategoryActivity() {
        Intent(this, SelectCategoryActivity::class.java)
            .apply {
                val cafeDetail = cafeDetailsViewModel.cafeDetailsEntity.value?.toCafeDetailEntity()
                val gson = Gson()
                putExtra(MapFragment.SELECTED_CAFE_INFO, gson.toJson(cafeDetail) )
            }
            .also { startActivity(it) }
    }

    private fun copyInstagramId() {
        binding.textviewInstgramId.run {
            setOnClickListener {
                this@CafeDetailsActivity.copyToClipBoard(text.toString())
                CustomToastBuilder(this@CafeDetailsActivity, "아이디가 복사되었습니다.", binding.constraintlayoutRoot)
                    .setBackgroundDrawable(R.drawable.background_capin_toast)
                    .setIcon(R.drawable.ic_checkbox_active_toast)
                    .build()
            }
        }
    }

    private fun goToModifyReview(cafeReview: CafeReview) {
        val intent = Intent(this, WriteReviewActivity::class.java)
        val gson = Gson()
        val review = cafeReview.run {
            MyReview(writerId, cafeId, cafeDetailsViewModel.cafeDetailsEntity.value?.cafeName ?: "", content, createDate.toString(), imageUrls, starRate, recommend.map { it.id })
        }
        val reviewSelected = gson.toJson(review)
        intent.putExtra(EDIT_REVIEW, reviewSelected)
        startActivity(intent)
    }

    private fun showResultToast() {
        cafeDetailsViewModel.isDeleteSuccess.observe(this) { success ->
            CustomToastBuilder(this, if (success) "리뷰가 삭제되었습니다." else "리뷰를 삭제하지 못하였습니다.", binding.constraintlayoutRoot).build()
        }
    }

    companion object {
        const val KEY_CAFE_ID = "KEY_CAFE_ID"
    }
}
