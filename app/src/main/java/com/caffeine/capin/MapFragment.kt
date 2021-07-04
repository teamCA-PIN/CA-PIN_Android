package com.caffeine.capin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.caffeine.capin.databinding.FragmentMapBinding
import com.caffeine.capin.preference.UserPreferenceManager
import com.caffeine.capin.util.AutoClearedValue


class MapFragment: Fragment() {
    private var binding by AutoClearedValue<FragmentMapBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}