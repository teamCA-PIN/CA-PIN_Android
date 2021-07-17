package com.caffeine.capin.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.caffeine.capin.databinding.FragmentMapProfileBinding
import com.caffeine.capin.login.ui.LoginActivity
import com.caffeine.capin.mypage.ui.MyPageActivity
import com.caffeine.capin.preference.UserPreferenceManager
import com.caffeine.capin.util.AutoClearedValue
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MapProfileFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentMapProfileBinding>()
    private val viewModel by viewModels<MapProfileViewModel>()
    @Inject lateinit var userPreferenceManager: UserPreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        logout()

        binding.constraintlayoutArchive.setOnClickListener {
            val intent = Intent(this@MapProfileFragment.requireActivity(), MyPageActivity::class.java)
            startActivity(intent)
        }
        binding.buttonExit.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun logout() {
        binding.textviewLogout.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            userPreferenceManager.setUserToken("")
            startActivity(intent)
        }
    }
}