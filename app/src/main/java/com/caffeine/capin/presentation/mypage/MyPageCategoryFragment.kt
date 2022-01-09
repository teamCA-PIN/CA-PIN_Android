package com.caffeine.capin.presentation.mypage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.caffeine.capin.R
import com.caffeine.capin.databinding.FragmentMyPageCategoryBinding
import com.caffeine.capin.data.dto.category.MyCategory
import com.caffeine.capin.data.local.UserPreferenceManager
import com.caffeine.capin.presentation.customview.CapinDialog
import com.caffeine.capin.presentation.customview.CapinDialogBuilder
import com.caffeine.capin.presentation.customview.CapinDialogButton
import com.caffeine.capin.presentation.customview.DialogClickListener
import com.caffeine.capin.presentation.util.AutoClearedValue
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyPageCategoryFragment : Fragment() {
    @Inject
    lateinit var userPreferenceManager: UserPreferenceManager
    private val viewModel by viewModels<MyPageViewModel>()
    private var binding by AutoClearedValue<FragmentMyPageCategoryBinding>()
    private lateinit var myCategoryAdapter: MyCategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageCategoryBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMyCategoryFromServer()

        myCategoryAdapter = MyCategoryAdapter()
        binding.mypageCategoryRcvInclude.categoryRcv.adapter = myCategoryAdapter

        updateCategoryList()
        deleteCategory()

        binding.mypageCategoryAddCl.setOnClickListener {
            val intent =
                Intent(
                    this@MyPageCategoryFragment.requireContext(),
                    MyPageCategoryEditActivity::class.java
                )
            intent.putExtra("feature", "새 카테고리")
            startActivity(intent)
        }

        myCategoryAdapter.setOnCategoryClickListener(object :
            MyCategoryAdapter.OnCategoryClickListener {
            override fun onCategoryClick(myCategory: MyCategory) {
                viewModel.changeRemoveCategoryInfo(myCategory)
                val intent = Intent(
                    this@MyPageCategoryFragment.requireContext(),
                    MyPagePinDetailActivity::class.java
                )
                val categoryName = myCategory.name
                intent.putExtra("name", categoryName)
                intent.putExtra("categoryPinId", viewModel.removeCategoryInfo.value?._id)
                startActivity(intent)
            }
        })

        myCategoryAdapter.setOnEditButtonClickListener(object :
            MyCategoryAdapter.OnEditButtonClickListener {
            override fun onEditButtonClick(myCategory: MyCategory) {
                viewModel.changeRemoveCategoryInfo(myCategory)
                showEditCategoryDialog()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMyCategoryFromServer()
        updateCategoryList()
    }

    private fun showEditCategoryDialog() {
        val categoryEditList = ArrayList<CapinDialogButton>()
        val dialog: CapinDialog = CapinDialogBuilder("카테고리 편집")
            .setButtonArray(categoryEditList)
            .setExitButton(true)
            .build()

        categoryEditList.apply {
            add(
                CapinDialogButton("카테고리 수정",
                    ContextCompat.getColor(
                        this@MyPageCategoryFragment.requireContext(),
                        R.color.maincolor_1
                    ), this@MyPageCategoryFragment.requireContext(),
                    object : CapinDialogButton.OnClickListener {
                        override fun onClick() {
                            val intent = Intent(
                                this@MyPageCategoryFragment.requireContext(),
                                MyPageCategoryEditActivity::class.java
                            )
                            intent.putExtra("feature", "카테고리 수정")
                            intent.putExtra("categoryId", viewModel.removeCategoryInfo.value?._id)
                            startActivity(intent)
                            dialog.dismiss()
                        }
                    })
            )
            add(
                CapinDialogButton("카테고리 삭제",
                    ContextCompat.getColor(
                        this@MyPageCategoryFragment.requireContext(),
                        R.color.pointcolor_red
                    ), this@MyPageCategoryFragment.requireContext(),
                    object : CapinDialogButton.OnClickListener {
                        override fun onClick() {
                            showDeleteCategoryConfirmDialog()
                            dialog.dismiss()
                        }
                    })
            )
        }

        dialog.show(childFragmentManager, "picture")
    }

    private fun showDeleteCategoryConfirmDialog() {
        val dialog: CapinDialog = CapinDialogBuilder(null)
            .setContentDialogTitile("카테고리를 삭제하시겠습니까?")
            .setContent("해당 카테고리에 저장된 모든 핀이 함께 삭제됩니다.")
            .setContentDialogButtons(true, object : DialogClickListener {
                override fun onClick() {
                    viewModel.deleteMyCategoryAtServer()
                    viewModel.getMyCategoryFromServer()

                }
            }).build()
        dialog.show(childFragmentManager, "DeleteReview")
    }

    private fun deleteCategory() {
        viewModel.isSuccessDeleteCategory.observe(viewLifecycleOwner) {
            viewModel.removeCategories()
        }
    }

    private fun updateCategoryList() {
        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            (binding.mypageCategoryRcvInclude.categoryRcv.adapter as MyCategoryAdapter).myCategoryList =
                categories as MutableList<MyCategory>
            (binding.mypageCategoryRcvInclude.categoryRcv.adapter as MyCategoryAdapter).notifyDataSetChanged()
            if (categories.size > 1) {
                binding.ifBasicCategoryTv.isVisible = false
            }
        }
    }
}