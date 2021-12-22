package com.caffeine.capin.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.caffeine.capin.BuildConfig
import com.caffeine.capin.R
import com.caffeine.capin.customview.CapinToastMessage.createCapinToast
import com.caffeine.capin.databinding.FragmentPolicyBinding
import com.caffeine.capin.login.ui.LoginActivity
import com.caffeine.capin.preference.UserPreferenceManager
import com.caffeine.capin.util.AutoClearedValue
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PolicyFragment: Fragment() {
    private var binding by AutoClearedValue<FragmentPolicyBinding>()
    @Inject lateinit var userPreferenceManager: UserPreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPolicyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textviewVersionValue.text = "최신 ${BuildConfig.VERSION_NAME}v"
        openTermsUrl()
        withDraw()
        setToolbar()
    }

    private fun setToolbar() {
        binding.toolbar.run {
            setToolbarTitle("약관 및 정책")
            setBackButton {
                findNavController().popBackStack()
            }
        }
    }

    private fun openTermsUrl() {
        binding.textviewServiceTerms.setOnClickListener {
            val termsURL = requireContext().getString(R.string.service_using_terms_url)
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(termsURL)))
        }
        binding.textviewPersonalInfoTerms.setOnClickListener {
            val termsURL = requireContext().getString(R.string.personal_info_terms_url)
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(termsURL)))
        }
    }

    private fun withDraw() {
        binding.textviewWithdraw.setOnClickListener {
            findNavController().navigate(R.id.action_policyFragment_to_withDrawFragment)
        }
    }
}