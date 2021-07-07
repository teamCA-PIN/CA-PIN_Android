package com.caffeine.capin.category

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.caffeine.capin.databinding.ItemRecyclerviewCategoryBinding

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    val categoryList = mutableListOf<Category>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.CategoryViewHolder {
        val binding = ItemRecyclerviewCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryAdapter.CategoryViewHolder(
            binding
        )
    }

    override fun getItemCount(): Int  = categoryList.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.onBind(categoryList[position])
    }

    class CategoryViewHolder(
        private val binding: ItemRecyclerviewCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(category: Category){
            binding.rcvCategoryColorIv.setBackgroundColor(Color.parseColor("#${category.color}"))
            binding.rcvCategoryNameTv.text = category.name
            binding.rcvCategoryPinNumTv.text = category.cafeNum.toString()

            if (category.name == "기본 카테고리") {
                binding.rcvCategoryEditBtn.isVisible = false
            }
        }
    }
}