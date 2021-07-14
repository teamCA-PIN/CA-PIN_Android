package com.caffeine.capin.mypage.mycategory

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.caffeine.capin.databinding.ItemRecyclerviewCategoryBinding

class MyCategoryAdapter : RecyclerView.Adapter<MyCategoryAdapter.ArchivingCategoryViewHolder>() {
    var myCategoryList = mutableListOf<MyCategory>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCategoryAdapter.ArchivingCategoryViewHolder {
        val binding = ItemRecyclerviewCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ArchivingCategoryViewHolder(binding)
    }
    override fun getItemCount(): Int  = myCategoryList.size

    override fun onBindViewHolder(holder: ArchivingCategoryViewHolder, position: Int) {
        holder.onBind(myCategoryList[position])
    }

    interface OnCategoryClickListener {
        fun onCategoryClick(myCategory: MyCategory)
    }

    private lateinit var categoryClickListener: OnCategoryClickListener

    fun setOnCategoryClickListener(listener: OnCategoryClickListener) {
        this.categoryClickListener = listener
    }

    interface OnEditButtonClickListener {
        fun onEditButtonClick(myCategory: MyCategory)
    }

    private lateinit var editButtonClickListener: OnEditButtonClickListener

    fun setOnEditButtonClickListener(listener: OnEditButtonClickListener) {
        this.editButtonClickListener = listener
    }

    inner class ArchivingCategoryViewHolder(
        private val binding: ItemRecyclerviewCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(myCategory: MyCategory){
            binding.rcvCategoryColorIv.setBackgroundColor(Color.parseColor("#${myCategory.color}"))
            binding.rcvCategoryNameTv.text = myCategory.name
            binding.rcvCategoryPinNumTv.text = myCategory.cafes.size.toString()

            if (myCategory.name == "기본 카테고리") {
                binding.rcvCategoryEditBtn.isVisible = false
            }

            binding.rcvCategoryNameTv.setOnClickListener {
                categoryClickListener.onCategoryClick(myCategory)
            }

            binding.rcvCategoryColorIv.setOnClickListener {
                categoryClickListener.onCategoryClick(myCategory)
            }

            binding.rcvCategoryEditBtn.setOnClickListener {
                editButtonClickListener.onEditButtonClick(myCategory)
            }
        }
    }
}