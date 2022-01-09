package com.caffeine.capin.presentation.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.caffeine.capin.databinding.FragmentMyReviewImageBinding
import com.caffeine.capin.presentation.util.AutoClearedValue

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
        Glide
            .with(binding.myreviewImageIv.context)
            .load(url)
            .into(binding.myreviewImageIv)
    }
}