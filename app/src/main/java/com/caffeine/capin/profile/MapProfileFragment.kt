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
import com.caffeine.capin.mypage.MyPageActivity
import com.caffeine.capin.util.AutoClearedValue
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapProfileFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentMapProfileBinding>()
    private val viewModel by viewModels<MapProfileViewModel>()

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

        binding.constraintlayoutArchive.setOnClickListener {
            val intent = Intent(this@MapProfileFragment.requireActivity(), MyPageActivity::class.java)
            startActivity(intent)
        }
        binding.buttonExit.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}