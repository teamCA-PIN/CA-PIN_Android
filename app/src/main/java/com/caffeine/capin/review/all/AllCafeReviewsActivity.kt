package com.caffeine.capin.review.all

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.caffeine.capin.databinding.ActivityAllCafeReviewsBinding
import com.caffeine.capin.mypage.myreview.MyReviewImageDialog
import com.caffeine.capin.review.CafeReviewsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllCafeReviewsActivity : AppCompatActivity() {
    private val cafeReviewsAdapter = CafeReviewsAdapter(object: CafeReviewsAdapter.ExpandImageInterface{
        override fun expand(images: List<String>) {
            MyReviewImageDialog(images as ArrayList<String>).show(supportFragmentManager, "SampleDialog")
        }
    })
    private val allCafeReviewsViewModel: AllCafeReviewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityAllCafeReviewsBinding.inflate(layoutInflater)
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
        allCafeReviewsViewModel.loadCafeDetails(getCafeId())
    }

    private fun getCafeId(): String {
        return intent.getStringExtra(KEY_CAFE_ID) ?: error("cafe id must be put in intent")
    }

    companion object {
        const val KEY_CAFE_ID = "KEY_CAFE_ID"
    }
}
