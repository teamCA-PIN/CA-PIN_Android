package com.caffeine.capin.mypage.myreview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.caffeine.capin.databinding.FragmentMyReviewImageBinding
import com.caffeine.capin.util.AutoClearedValue

class MyReviewImageFragment(val url: String) : Fragment() {

    private var binding by AutoClearedValue<FragmentMyReviewImageBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyReviewImageBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("리미 왜 안돼", url)
        Glide
            .with(binding.myreviewImageIv.context)
            .load(url)
            .into(binding.myreviewImageIv)
    }
}