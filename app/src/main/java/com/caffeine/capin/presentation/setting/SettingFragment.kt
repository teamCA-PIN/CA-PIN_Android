package com.caffeine.capin.presentation.setting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.caffeine.capin.R
import com.caffeine.capin.databinding.FragmentSettingBinding
import com.caffeine.capin.presentation.login.LoginActivity
import com.caffeine.capin.data.local.UserPreferenceManager
import com.caffeine.capin.presentation.util.AutoClearedValue
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentSettingBinding>()
    @Inject lateinit var userPreferenceManager: UserPreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner

        logout()

        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.textviewTerms.setOnClickListener {
            findNavController().navigate(R.id.action_setting_to_policyFragment)
        }
    }

    private fun logout() {
        binding.textviewLogout.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            userPreferenceManager.run {
                setUserAccessToken("")
                setUserRefreshToken("")
                setUserEmail("")
                setUserPassword("")
            }
            startActivity(intent)
        }
    }
}