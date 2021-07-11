package com.caffeine.capin.mypage.archivingcategory

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.caffeine.capin.databinding.ItemRecyclerviewCategoryBinding

class ArchivingCategoryAdapter : RecyclerView.Adapter<ArchivingCategoryAdapter.ArchivingCategoryViewHolder>() {

    val archivingCategoryList = mutableListOf<ArchivingCategory>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArchivingCategoryAdapter.ArchivingCategoryViewHolder {
        val binding = ItemRecyclerviewCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ArchivingCategoryViewHolder(binding)
    }
    override fun getItemCount(): Int  = archivingCategoryList.size

    override fun onBindViewHolder(holder: ArchivingCategoryViewHolder, position: Int) {
        holder.onBind(archivingCategoryList[position])
    }

    interface OnCategoryClickListener {
        fun onCategoryClick(archivingCategory: ArchivingCategory)
    }

    private lateinit var categoryClickListener: OnCategoryClickListener

    fun setOnCategoryClickListener(listener: OnCategoryClickListener) {
        this.categoryClickListener = listener
    }

    interface OnEditButtonClickListener {
        fun onEditButtonClick()
    }

    private lateinit var editButtonClickListener: OnEditButtonClickListener

    fun setOnEditButtonClickListener(listener: OnEditButtonClickListener) {
        this.editButtonClickListener = listener
    }

    inner class ArchivingCategoryViewHolder(
        private val binding: ItemRecyclerviewCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(archivingCategory: ArchivingCategory){
            binding.rcvCategoryColorIv.setBackgroundColor(Color.parseColor("#${archivingCategory.color}"))
            binding.rcvCategoryNameTv.text = archivingCategory.name
            binding.rcvCategoryPinNumTv.text = archivingCategory.cafeNum.toString()

            if (archivingCategory.name == "기본 카테고리") {
                binding.rcvCategoryEditBtn.isVisible = false
            }

            binding.rcvCategoryNameTv.setOnClickListener {
                categoryClickListener.onCategoryClick(archivingCategory)
            }

            binding.rcvCategoryColorIv.setOnClickListener {
                categoryClickListener.onCategoryClick(archivingCategory)
            }

            binding.rcvCategoryEditBtn.setOnClickListener {
                editButtonClickListener.onEditButtonClick()
            }
        }
    }
}