package com.caffeine.capin.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.caffeine.capin.R
import com.caffeine.capin.category.Category
import com.caffeine.capin.category.CategoryAdapter
import com.caffeine.capin.databinding.FragmentMapBinding
import com.caffeine.capin.databinding.FragmentMyPageCategoryBinding
import com.caffeine.capin.databinding.FragmentMyPageReviewBinding
import com.caffeine.capin.util.AutoClearedValue

class MyPageCategoryFragment : Fragment() {

    private var binding by AutoClearedValue<FragmentMyPageCategoryBinding>()

    private lateinit var categoryAdapter: CategoryAdapter

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

        categoryAdapter = CategoryAdapter()
        binding.mypageCategoryRcvInclude.categoryRcv.adapter = categoryAdapter

        categoryAdapter.categoryList.addAll(
            listOf<Category>(
                Category(
                    color = 1,
                    name = "기본 카테고리",
                    cafeNum = 1
                ),
                Category(
                    color = 1,
                    name = "카테고리1",
                    cafeNum = 2
                ),
                Category(
                    color = 1,
                    name = "카테고리2",
                    cafeNum = 3
                ),
                Category(
                    color = 1,
                    name = "카테고리3",
                    cafeNum = 3
                )
            )
        )
        categoryAdapter.notifyDataSetChanged()
    }
}