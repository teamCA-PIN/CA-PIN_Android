package com.caffeine.capin.presentation.cafeti.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.caffeine.capin.data.local.UserPreferenceManager
import com.caffeine.capin.databinding.FragmentCafetiResultBinding
import com.caffeine.capin.presentation.cafeti.viewModel.CafetiResultViewModel
import com.caffeine.capin.presentation.main.MainActivity
import com.caffeine.capin.presentation.mypage.MyPageActivity
import com.caffeine.capin.presentation.mypage.MyPageActivity.Companion.FROM_MYPAGE
import com.caffeine.capin.presentation.util.AutoClearedValue
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CafetiResultFragment: Fragment() {
    private var binding by AutoClearedValue<FragmentCafetiResultBinding>()
    private val args: CafetiResultFragmentArgs by navArgs()
    private val viewModel: CafetiResultViewModel by viewModels()
    @Inject lateinit var userPreferenceManager: UserPreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentCafetiResultBinding.inflate(inflater, container, false)?.let {
        binding = it
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.changeCafetiResult(args.responseCafeti.result)
        userPreferenceManager.setNeedCafetiCheck(false)
        cafetiTestFinishButtonClickEvent()
    }

    private fun cafetiTestFinishButtonClickEvent() {
        binding.btnCafetitestfinish.setOnClickListener {
            val intent = Intent(
                requireContext(),
                if (requireActivity().intent.getBooleanExtra(FROM_MYPAGE, false)) MyPageActivity::class.java else MainActivity::class.java
            )
            startActivity(intent)
        }
    }
}