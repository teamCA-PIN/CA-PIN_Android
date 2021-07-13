package com.caffeine.capin.mypage

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.caffeine.capin.R
import com.caffeine.capin.customview.CapinDialog
import com.caffeine.capin.customview.CapinDialogBuilder
import com.caffeine.capin.customview.CapinDialogButton
import com.caffeine.capin.customview.DialogClickListener
import com.caffeine.capin.mypage.mycategory.MyCategory
import com.caffeine.capin.mypage.mycategory.MyCategoryAdapter
import com.caffeine.capin.databinding.FragmentMyPageCategoryBinding
import com.caffeine.capin.mypage.mycategory.MyPageCategoryEditActivity
import com.caffeine.capin.mypage.pin.MyPagePinDetailActivity
import com.caffeine.capin.util.AutoClearedValue

class MyPageCategoryFragment : Fragment() {

    private var binding by AutoClearedValue<FragmentMyPageCategoryBinding>()

    private lateinit var myCategoryAdapter: MyCategoryAdapter

    private lateinit var removeCategoryInfo: MyCategory

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

        binding.mypageCategoryAddCl.setOnClickListener {
            val intent = Intent(this@MyPageCategoryFragment.activity, MyPageCategoryEditActivity::class.java)
            intent.putExtra("feature", "새 카테고리")
            startActivity(intent)
        }

        myCategoryAdapter = MyCategoryAdapter()
        binding.mypageCategoryRcvInclude.categoryRcv.adapter = myCategoryAdapter

        myCategoryAdapter.setOnCategoryClickListener(object :
            MyCategoryAdapter.OnCategoryClickListener{
            override fun onCategoryClick(myCategory: MyCategory) {
                val intent = Intent(this@MyPageCategoryFragment.activity, MyPagePinDetailActivity::class.java)
                val cafeName = myCategory.name
                intent.putExtra("name", cafeName)
                startActivity(intent)
            }
        })

        myCategoryAdapter.setOnEditButtonClickListener(object :
            MyCategoryAdapter.OnEditButtonClickListener {
            override fun onEditButtonClick(myCategory: MyCategory) {
                removeCategoryInfo = myCategory
                showEditCategoryDialog()
            }
        })

        myCategoryAdapter.myCategoryList.addAll(
            listOf<MyCategory>(
                MyCategory(
                    color = "6492f5",
                    name = "기본 카테고리",
                    cafeNum = 1
                ),

                MyCategory(
                    color = "6bbc9a",
                    name = "카테고리1",
                    cafeNum = 2
                ),
                MyCategory(
                    color = "ffc24b",
                    name = "카테고리2",
                    cafeNum = 3
                ),
                MyCategory(
                    color = "816f7c",
                    name = "카테고리3",
                    cafeNum = 3
                ),
                MyCategory(
                    color = "ffc2d5",
                    name = "카테고리4",
                    cafeNum = 3
                ),
                MyCategory(
                    color = "c9d776",
                    name = "카테고리5",
                    cafeNum = 3
                ),
                MyCategory(
                    color = "b2b9e5",
                    name = "카테고리6",
                    cafeNum = 3
                ),
                MyCategory(
                    color = "ff8e8e",
                    name = "카테고리7",
                    cafeNum = 3
                ),
                MyCategory(
                    color = "ebeaef",
                    name = "카테고리8",
                    cafeNum = 3
                ),
                MyCategory(
                    color = "9dc5e8",
                    name = "카테고리9",
                    cafeNum = 3
                )
            )
        )
        myCategoryAdapter.notifyDataSetChanged()

        if(myCategoryAdapter.myCategoryList.size > 1) {
            binding.ifBasicCategoryTv.isVisible = false
        }
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
                    ContextCompat.getColor(this@MyPageCategoryFragment.requireContext(), R.color.maincolor_1), this@MyPageCategoryFragment.requireContext(),
                    object : CapinDialogButton.OnClickListener {
                        override fun onClick() {
                            val intent = Intent(this@MyPageCategoryFragment.activity, MyPageCategoryEditActivity::class.java)
                            intent.putExtra("feature", "카테고리 수정")
                            startActivity(intent)
                            dialog.dismiss()
                        }
                    })
            )
            add(
                CapinDialogButton("카테고리 삭제",
                    ContextCompat.getColor(this@MyPageCategoryFragment.requireContext(), R.color.pointcolor_red), this@MyPageCategoryFragment.requireContext(),
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
            .setContentDialogButtons(true, object: DialogClickListener {
                override fun onClick() {
                    myCategoryAdapter.myCategoryList.remove(removeCategoryInfo)
                    myCategoryAdapter.notifyDataSetChanged()
                }
            }).build()
        dialog.show(childFragmentManager, "DeleteReview")
    }
}