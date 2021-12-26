package com.caffeine.capin.detail

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.caffeine.capin.R
import com.caffeine.capin.category.ui.SelectCategoryActivity
import com.caffeine.capin.customview.CustomToastTextView
import com.caffeine.capin.databinding.ActivityCafeDetailsBinding
import com.caffeine.capin.detail.menus.CafeMenusActivity
import com.caffeine.capin.review.CafeReviewsAdapter
import com.caffeine.capin.review.ReviewTagAdapter
import com.caffeine.capin.review.all.AllCafeReviewsActivity
import com.caffeine.capin.review.write.ui.WriteReviewActivity
import com.caffeine.capin.util.HorizontalItemDecoration
import com.caffeine.capin.util.copyToClipBoard
import com.google.android.flexbox.*
import com.google.android.material.appbar.AppBarLayout
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

        val adapter = CafeReviewsAdapter()
        binding.listReviews.adapter = adapter

        loadCafeTags()
        checkToolbarCollapsed()
        copyInstagramId()

        binding.buttonMenus.setOnClickListener { deployMenusActivity() }
        binding.imageviewBack.setOnClickListener { finish() }
        binding.buttonAllReviews.setOnClickListener { deployAllCafeReviewsActivity() }
        binding.buttonWriteReview.setOnClickListener { deployWriteReviewActivity() }
        binding.layoutSavePinButton.root.setOnClickListener { deploySelectCategoryActivity() }

        cafeDetailsViewModel.cafeReviews.observe(this) { adapter.submitList(it) }
    }

    override fun onResume() {
        super.onResume()
        cafeDetailsViewModel.loadCafeDetails(getCafeId())
    }

    private fun checkToolbarCollapsed() {
        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if ((binding.collapsingToolbar.getHeight() + verticalOffset) < (2 * ViewCompat.getMinimumHeight(binding.collapsingToolbar))) {
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
            .apply { putExtra(WriteReviewActivity.KEY_CAFE_ID, getCafeId()) }
            .also { startActivity(it) }
    }

    private fun deploySelectCategoryActivity() {
        Intent(this, SelectCategoryActivity::class.java)
            .also { startActivity(it) }
    }

    private fun copyInstagramId() {
        binding.textviewInstgramId.run {
            setOnClickListener {
                this@CafeDetailsActivity.copyToClipBoard(text.toString())
                CustomToastTextView(this@CafeDetailsActivity, null, "아이디가 복사되었습니다.", null, 0.9, binding.constraintlayoutRoot)
            }
        }
    }

    companion object {
        const val KEY_CAFE_ID = "KEY_CAFE_ID"
    }
}
