package com.caffeine.capin.tagfilter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.caffeine.capin.R
import com.caffeine.capin.databinding.FragmentTagFilterBinding
import com.caffeine.capin.util.AutoClearedValue
import com.caffeine.capin.util.VerticalItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TagFilterFragment : Fragment() {
    private var binding by AutoClearedValue<FragmentTagFilterBinding>()
    private val viewModel by viewModels<TagFilterViewModel>()

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
        activeGetResultButton()
        getFilterCafe()
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

    private fun activeGetResultButton() {
        viewModel.filterChecked.observe(viewLifecycleOwner) { tagFilter ->
            if (!tagFilter.isNullOrEmpty()) {
                changeResultButtonStyle(R.color.pointcolor_1, R.color.white)
            } else {
                changeResultButtonStyle(R.color.gray_1, R.color.gray_4)
            }
        }
    }

    private fun getFilterCafe() {
        viewModel.checkedTagList.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                if (it.all { it == null }) {
                    viewModel.updateCountCafeResult(0)
                } else {
                    Log.e("checkedTagList","${it}}")
                    viewModel.getCafeSize()
                }
            } else {
                viewModel.updateCountCafeResult(0)
            }
        }
    }

    private fun changeResultButtonStyle(color: Int, textColor: Int) {
        binding.buttonResult.apply {
            background = ContextCompat.getDrawable(requireContext(), color)
            setTextColor(ContextCompat.getColor(requireContext(), textColor))
        }
    }
}