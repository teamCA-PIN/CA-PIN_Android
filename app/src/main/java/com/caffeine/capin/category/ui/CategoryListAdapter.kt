package com.caffeine.capin.category.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.caffeine.capin.BR
import com.caffeine.capin.category.CategoryNameEntity
import com.caffeine.capin.databinding.ItemSelectCategoryBinding

class CategoryListAdapter(val listener: CategorySelectListener) : ListAdapter<CategoryNameEntity, CategoryListAdapter.CategoryListViewHolder>(
    diffCallback
) {
    interface CategorySelectListener {
        fun selectCategory(categoryNameEntity: CategoryNameEntity)
    }
    private var selectedPosition = -1
    private var lastItemSelectPosition = -1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryListViewHolder {
        val binding = ItemSelectCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryListViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.setVariable(BR.data, item)
        holder.binding.checkboxSelect.isChecked = position == selectedPosition

        holder.binding.checkboxSelect.setOnClickListener{
            selectedPosition = position
            if (selectedPosition == -1) {
                lastItemSelectPosition = selectedPosition
            } else {
                notifyItemChanged(lastItemSelectPosition)
                lastItemSelectPosition = selectedPosition
            }
            listener.selectCategory(item)
            notifyItemChanged(position)
        }

    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<CategoryNameEntity>(){
            override fun areItemsTheSame(
                oldItem: CategoryNameEntity,
                newItem: CategoryNameEntity
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(
                oldItem: CategoryNameEntity,
                newItem: CategoryNameEntity
            ): Boolean {
               return oldItem == newItem
            }
        }
    }

    class CategoryListViewHolder(val binding: ItemSelectCategoryBinding): RecyclerView.ViewHolder(binding.root)
}