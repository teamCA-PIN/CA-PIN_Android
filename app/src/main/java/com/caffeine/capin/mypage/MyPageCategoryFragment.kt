package com.caffeine.capin.mypage

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.caffeine.capin.mypage.archivingcategory.ArchivingCategory
import com.caffeine.capin.mypage.archivingcategory.ArchivingCategoryAdapter
import com.caffeine.capin.databinding.FragmentMyPageCategoryBinding
import com.caffeine.capin.mypage.archivingcategory.MyPageCategoryEditActivity
import com.caffeine.capin.mypage.pin.MyPagePinDetailActivity
import com.caffeine.capin.util.AutoClearedValue

class MyPageCategoryFragment : Fragment() {

    private var binding by AutoClearedValue<FragmentMyPageCategoryBinding>()

    private lateinit var archivingCategoryAdapter: ArchivingCategoryAdapter

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

        archivingCategoryAdapter = ArchivingCategoryAdapter()
        binding.mypageCategoryRcvInclude.categoryRcv.adapter = archivingCategoryAdapter

        archivingCategoryAdapter.setOnCategoryClickListener(object :
            ArchivingCategoryAdapter.OnCategoryClickListener{
            override fun onCategoryClick(archivingCategory: ArchivingCategory) {
                val intent = Intent(this@MyPageCategoryFragment.activity, MyPagePinDetailActivity::class.java)
                val cafeName = archivingCategory.name
                intent.putExtra("name", cafeName)
                startActivity(intent)
            }
        })

        archivingCategoryAdapter.setOnEditButtonClickListener(object :
            ArchivingCategoryAdapter.OnEditButtonClickListener {
            override fun onEditButtonClick() {
                val intent = Intent(this@MyPageCategoryFragment.activity, MyPageCategoryEditActivity::class.java)
                intent.putExtra("feature", "카테고리 수정")
                startActivity(intent)
            }
        })

        archivingCategoryAdapter.archivingCategoryList.addAll(
            listOf<ArchivingCategory>(
                ArchivingCategory(
                    color = "6492f5",
                    name = "기본 카테고리",
                    cafeNum = 1
                ),

                ArchivingCategory(
                    color = "6bbc9a",
                    name = "카테고리1",
                    cafeNum = 2
                ),
                ArchivingCategory(
                    color = "ffc24b",
                    name = "카테고리2",
                    cafeNum = 3
                ),
                ArchivingCategory(
                    color = "816f7c",
                    name = "카테고리3",
                    cafeNum = 3
                ),
                ArchivingCategory(
                    color = "ffc2d5",
                    name = "카테고리4",
                    cafeNum = 3
                ),
                ArchivingCategory(
                    color = "c9d776",
                    name = "카테고리5",
                    cafeNum = 3
                ),
                ArchivingCategory(
                    color = "b2b9e5",
                    name = "카테고리6",
                    cafeNum = 3
                ),
                ArchivingCategory(
                    color = "ff8e8e",
                    name = "카테고리7",
                    cafeNum = 3
                ),
                ArchivingCategory(
                    color = "ebeaef",
                    name = "카테고리8",
                    cafeNum = 3
                ),
                ArchivingCategory(
                    color = "9dc5e8",
                    name = "카테고리9",
                    cafeNum = 3
                )
            )
        )
        archivingCategoryAdapter.notifyDataSetChanged()

        if(archivingCategoryAdapter.archivingCategoryList.size > 1) {
            binding.ifBasicCategoryTv.isVisible = false
        }
    }
}