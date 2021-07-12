package com.caffeine.capin.mypage.archivingcategory

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.caffeine.capin.databinding.ItemMypageCategoryColorBinding
import com.caffeine.capin.databinding.ItemMypageReviewBinding
import com.caffeine.capin.mypage.myreview.MyReview

class CategoryColorAdapter : RecyclerView.Adapter<CategoryColorAdapter.CategoryColorViewHolder>() {

    val categoryColorList = mutableListOf<CategoryColor>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryColorAdapter.CategoryColorViewHolder {
        val binding = ItemMypageCategoryColorBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryColorViewHolder(binding)
    }
    override fun getItemCount(): Int  = categoryColorList.size

    override fun onBindViewHolder(holder: CategoryColorViewHolder, position: Int) {
        holder.onBind(categoryColorList[position])
    }

    interface OnSigleCheckListener {
        fun onSingleCheck(categoryColor: CategoryColor)
    }

    private lateinit var singleCheckListener: OnSigleCheckListener

    fun setOnSigleCheckListener(listener: OnSigleCheckListener) {
        this.singleCheckListener = listener
    }

    inner class CategoryColorViewHolder(
        private val binding: ItemMypageCategoryColorBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(categoryColor: CategoryColor){
            binding.categoryColorRadioBtn.setBackgroundResource(categoryColor.color)

            binding.categoryColorRadioBtn.setOnClickListener {
                singleCheckListener.onSingleCheck(categoryColor)
            }
        }
    }
}