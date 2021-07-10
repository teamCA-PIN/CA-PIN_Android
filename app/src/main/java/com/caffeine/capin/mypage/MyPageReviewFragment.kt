package com.caffeine.capin.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.caffeine.capin.R
import com.caffeine.capin.databinding.FragmentMyPageCategoryBinding
import com.caffeine.capin.databinding.FragmentMyPageReviewBinding
import com.caffeine.capin.util.AutoClearedValue


class MyPageReviewFragment : Fragment() {

    private var binding by AutoClearedValue<FragmentMyPageReviewBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageReviewBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }
}