package com.caffeine.capin.detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.caffeine.capin.R
import com.caffeine.capin.category.ui.SelectCategoryActivity
import com.caffeine.capin.customview.CustomToastBuilder
import com.caffeine.capin.databinding.ActivityCafeDetailsBinding
import com.caffeine.capin.detail.menus.CafeMenusActivity
import com.caffeine.capin.map.MapFragment
import com.caffeine.capin.mypage.myreview.MyReviewImageDialog
import com.caffeine.capin.review.CafeReviewsAdapter
import com.caffeine.capin.review.ReviewTagAdapter
import com.caffeine.capin.review.all.AllCafeReviewsActivity
import com.caffeine.capin.review.write.ui.WriteReviewActivity
import com.caffeine.capin.review.write.ui.WriteReviewActivity.Companion.EDIT_REVIEW
import com.caffeine.capin.util.HorizontalItemDecoration
import com.caffeine.capin.util.copyToClipBoard
import com.google.android.flexbox.*
import com.google.android.material.appbar.AppBarLayout
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CafeDetailsActivity : AppCompatActivity() {
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
        })
        binding.listReviews.adapter = adapter
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
            cafeDetailsViewModel.cafeDetails.observe(this@CafeDetailsActivity) {
                (this.adapter as ReviewTagAdapter).submitList(it.tags)
            }
        }
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
            .apply { putExtra(AllCafeReviewsActivity.KEY_CAFE_ID, getCafeId()) }
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
                val cafeDetail = cafeDetailsViewModel.cafeDetails.value?.toCafeDetailEntity()
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

    companion object {
        const val KEY_CAFE_ID = "KEY_CAFE_ID"
    }
}
