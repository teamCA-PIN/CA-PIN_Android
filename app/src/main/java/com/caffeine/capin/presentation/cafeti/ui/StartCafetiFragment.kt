package com.caffeine.capin.presentation.cafeti.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.caffeine.capin.R
import com.caffeine.capin.data.local.UserPreferenceManager
import com.caffeine.capin.databinding.FragmentStartCafetiBinding
import com.caffeine.capin.presentation.main.MainActivity
import com.caffeine.capin.presentation.mypage.MyPageActivity
import com.caffeine.capin.presentation.util.AutoClearedValue
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StartCafetiFragment: Fragment() {
    private var binding by AutoClearedValue<FragmentStartCafetiBinding>()
    @Inject lateinit var userPreferenceManager: UserPreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartCafetiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startCafeti()
        closeCafeti()
    }

    private fun startCafeti() {
        binding.buttonStartCafeti.setOnClickListener {
            findNavController().navigate(R.id.action_startCafetiFragment_to_cafetiQuestionFragment)
        }
    }
    private fun closeCafeti() {
        binding.btnclose.setOnClickListener {
            userPreferenceManager.setNeedCafetiCheck(false)
            val intent = Intent(
                requireContext(),
                if (requireActivity().intent.getBooleanExtra(MyPageActivity.FROM_MYPAGE, false)) MyPageActivity::class.java else MainActivity::class.java
            )
            startActivity(intent)
        }
    }
}