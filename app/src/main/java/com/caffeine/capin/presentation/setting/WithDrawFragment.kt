package com.caffeine.capin.presentation.setting

import android.content.Intent
import android.os.Bundle
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.toSpannable
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.caffeine.capin.R
import com.caffeine.capin.presentation.customview.CapinToastMessage.createCapinToast
import com.caffeine.capin.databinding.FragmentWithDrawBinding
import com.caffeine.capin.presentation.login.LoginActivity
import com.caffeine.capin.data.local.UserPreferenceManager
import com.caffeine.capin.presentation.util.AutoClearedValue
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WithDrawFragment: Fragment() {
    @Inject lateinit var userPreferenceManager: UserPreferenceManager
    private var binding by AutoClearedValue<FragmentWithDrawBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWithDrawBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setNoticeSpan()
        setToolbar()
        checkWithDrawApproval()
        withDraw()
    }

    private fun setToolbar() {
        binding.toolbar.run {
            setToolbarTitle("회원탈퇴")
            setBackButton {
                findNavController().popBackStack()
            }
        }
    }

    private fun setNoticeSpan() {
        binding.textviewWithdrawNotice.run {
            val span = text.toSpannable()
            span.setSpan(UnderlineSpan(), 30, 38, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            span.setSpan(ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.black)), 30 , 38, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

    private fun checkWithDrawApproval() {
        binding.checkboxApprove.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.buttonWithDraw.isActivated = isChecked
        }
    }

    private fun withDraw() {
        binding.buttonWithDraw.setOnClickListener {
            with(userPreferenceManager) {
                setUserAccessToken("")
                setUserRefreshToken("")
            }
            createCapinToast(requireContext(), "이용해주셔서 감사합니다.", 100)?.show()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }
    }
}