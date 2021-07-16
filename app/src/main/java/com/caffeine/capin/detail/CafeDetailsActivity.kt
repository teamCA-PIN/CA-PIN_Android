package com.caffeine.capin.detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.caffeine.capin.category.ui.SelectCategoryActivity
import com.caffeine.capin.databinding.ActivityCafeDetailsBinding
import com.caffeine.capin.detail.menus.CafeMenusActivity
import com.caffeine.capin.review.CafeReviewsAdapter
import com.caffeine.capin.review.all.AllCafeReviewsActivity
import com.caffeine.capin.review.write.ui.WriteReviewActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CafeDetailsActivity : AppCompatActivity() {

    private val cafeDetailsViewModel: CafeDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityCafeDetailsBinding.inflate(layoutInflater)
        binding.viewModel = cafeDetailsViewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)

        val adapter = CafeReviewsAdapter()
        binding.listReviews.adapter = adapter
        binding.buttonMenus.setOnClickListener { deployMenusActivity() }
        binding.buttonBack.setOnClickListener { finish() }
        binding.buttonAllReviews.setOnClickListener { deployAllCafeReviewsActivity() }
        binding.buttonWriteReview.setOnClickListener { deployWriteReviewActivity() }
        binding.buttonAddPin.setOnClickListener { deploySelectCategoryActivity() }

        cafeDetailsViewModel.cafeReviews.observe(this) { adapter.submitList(it) }
        cafeDetailsViewModel.loadCafeDetails(getCafeId())
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

    companion object {
        const val KEY_CAFE_ID = "KEY_CAFE_ID"
    }
}
