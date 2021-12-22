package com.caffeine.capin.tagfilter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.caffeine.capin.R
import com.caffeine.capin.customview.CapinDialog
import com.caffeine.capin.customview.CapinDialogBuilder
import com.caffeine.capin.customview.DialogClickListener
import com.caffeine.capin.databinding.FragmentTagFilterBinding
import com.caffeine.capin.map.MapViewModel
import com.caffeine.capin.tagfilter.model.TagFilterEntity
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
        initializeTag()
    }

    private fun initializeTag() {
        if (!viewModel.checkedTagList.value.isNullOrEmpty()) {
            (binding.recyclerviewTagFilter.adapter as TagFilterAdapter).checkTagList = viewModel.checkedTagList.value!!
            viewModel.getCapinMapPins()
        }
    }

    private fun setTagFilterButton() {
        binding.recyclerviewTagFilter.apply {
            addItemDecoration(VerticalItemDecoration(16))
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
        }
    }

    private fun activeResultButton() {
        viewModel.countCafeResult.observe(viewLifecycleOwner) { count ->
            binding.buttonResult.run {
                if(count != 0) {
                    changeResultButtonStyle(R.color.pointcolor_1, R.color.white)
                    text = resources.getString(R.string.tagfilter_button, viewModel.countCafeResult.value)
                    isClickable = true
                } else {
                    changeResultButtonStyle(R.color.gray_1, R.color.gray_4)
                    text = resources.getString(R.string.tagfilter_button_no_result)
                    isClickable = false
                }
            }
        }
    }

    private fun getFilterCafe() {
        viewModel.checkedTagList.observe(viewLifecycleOwner) { checkedList ->
            if(viewModel.isMyMap.value == true) {
                viewModel.getMyMapPins()
            } else {
                viewModel.getCapinMapPins()
            }
        }
    }

    private fun showResultInMapView() {
        binding.buttonResult.setOnClickListener { findNavController().popBackStack() }
    }

    private fun closeTagFilterFragment() {
        val dialog: CapinDialog = CapinDialogBuilder(null)
            .setContentDialogTitile(resources.getString(R.string.exit_page_tagfilter))
            .setContent(resources.getString(R.string.exit_page_content_tagfilter))
            .setContentDialogButtons(true, object : DialogClickListener {
                override fun onClick() {
                    viewModel.initializeFilterTag()
                    viewModel.getCapinMapPins()
                    findNavController().previousBackStackEntry?.savedStateHandle?.set("tagList", viewModel.checkedTagList.value)
                    findNavController().popBackStack()
                }
            }).build()

        binding.imageviewButtonClose.setOnClickListener {
            dialog.show(childFragmentManager,this.tag)
        }
    }

    private fun changeResultButtonStyle(color: Int, textColor: Int) {
        binding.buttonResult.apply {
            background = ContextCompat.getDrawable(requireContext(), color)
            setTextColor(ContextCompat.getColor(requireContext(), textColor))
        }
    }
}