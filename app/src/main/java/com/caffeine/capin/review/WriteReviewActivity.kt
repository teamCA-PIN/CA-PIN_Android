package com.caffeine.capin.review

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.caffeine.capin.databinding.ActivityWriteReviewBinding

class WriteReviewActivity: AppCompatActivity() {
    private lateinit var binding: ActivityWriteReviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteReviewBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.toolbar.apply {
            setToolbarTitle("리뷰 작성하기")
            setBackButton{

            }
        }
    }
}