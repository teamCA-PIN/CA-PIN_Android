package com.caffeine.capin.tagfilter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.caffeine.capin.R
import com.caffeine.capin.databinding.FragmentTagFilterBinding
import com.caffeine.capin.map.MapViewModel
import com.caffeine.capin.util.AutoClearedValue
import com.caffeine.capin.util.VerticalItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TagFilterFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentTagFilterBinding>()
    private val viewModel by activityViewModels<MapViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTagFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.recyclerviewTagFilter.isNestedScrollingEnabled = false
        setTagFilterButton()
        activeResultButton()
        getFilterCafe()
        showResultInMapView()
        closeTagFilterFragment()
        checkIsTagApplied()
        initializeTag()
    }

    private fun initializeTag() {
        if (!viewModel.checkedTagList.value.isNullOrEmpty()) {
            Log.e("initial", "${(binding.recyclerviewTagFilter.adapter as TagFilterAdapter).checkTagList}")
            Log.e("checkedList", "${viewModel.checkedTagList.value}")
            (binding.recyclerviewTagFilter.adapter as TagFilterAdapter).checkTagList = viewModel.checkedTagList.value!!
            viewModel.getCafeLocations()
        }
    }

    private fun setTagFilterButton() {
        binding.recyclerviewTagFilter.apply {
            adapter = TagFilterAdapter(object : TagFilterAdapter.FilterClickListener {
                override fun selectFilter(checkbox: CompoundButton, tag: TagFilterEntity) {
                    viewModel.addFilterChecked(checkbox)
                    viewModel.addTagList(tag)
                }

                override fun unSelectFilter(checkbox: CompoundButton, tag: TagFilterEntity) {
                    viewModel.removeFilterChecked(checkbox)
                    viewModel.removeTagList(tag)
                }
            })
            addItemDecoration(VerticalItemDecoration(16))
        }
    }

    private fun activeResultButton() {
        viewModel.filterChecked.observe(viewLifecycleOwner) { tagFilter ->
            if (!tagFilter.isNullOrEmpty()) {
                changeResultButtonStyle(R.color.pointcolor_1, R.color.white)
                binding.buttonResult.isClickable = true
            } else {
                changeResultButtonStyle(R.color.gray_1, R.color.gray_4)
                binding.buttonResult.isClickable = false
            }
        }
    }

    private fun checkIsTagApplied() {
        if (!viewModel.filterChecked.value.isNullOrEmpty()) {
            changeResultButtonStyle(R.color.pointcolor_1, R.color.white)
            binding.buttonResult.isClickable = true
        } else {
            changeResultButtonStyle(R.color.gray_1, R.color.gray_4)
            binding.buttonResult.isClickable = false
        }
    }

    private fun getFilterCafe() {
        viewModel.checkedTagList.observe(viewLifecycleOwner) { checkedList ->
            viewModel.getCafeLocations()
        }
    }

    private fun showResultInMapView() {
        binding.buttonResult.setOnClickListener { findNavController().popBackStack() }
    }

    private fun closeTagFilterFragment() {
        binding.imageviewButtonClose.setOnClickListener {
            findNavController().previousBackStackEntry?.savedStateHandle?.set("tagList", viewModel.checkedTagList.value)
            findNavController().popBackStack()
        }
    }

    private fun changeResultButtonStyle(color: Int, textColor: Int) {
        binding.buttonResult.apply {
            background = ContextCompat.getDrawable(requireContext(), color)
            setTextColor(ContextCompat.getColor(requireContext(), textColor))
        }
    }
}