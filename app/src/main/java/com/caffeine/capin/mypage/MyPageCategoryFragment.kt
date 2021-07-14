package com.caffeine.capin.mypage

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
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
            categoryCreateActivityLauncher.launch(intent)
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
                            categoryEditActivityLauncher.launch(intent)
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

    private val categoryEditActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val intent = it.data
            val editName = intent?.getStringExtra("categoryName")
            val editColor = intent?.getStringExtra("categoryColor")
            Log.d("리미-돌아와서 edit", editName.toString())
            Log.d("리미-돌아와서 edit", editColor.toString())

            removeCategoryInfo.name = editName.toString()
            removeCategoryInfo.color = editColor.toString()

        }
    }

    private val categoryCreateActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val intent = it.data
            val createName = intent?.getStringExtra("categoryName")
            val createColor = intent?.getStringExtra("categoryColor")
            Log.d("리미-돌아와서 create", createName.toString())
            Log.d("리미-돌아와서 create", createColor.toString())

            myCategoryAdapter.myCategoryList.add(
                MyCategory(
                    color = createColor.toString(),
                    name = createName.toString(),
                    cafeNum = 0
                )
            )
            myCategoryAdapter.notifyDataSetChanged()
        }
    }
}