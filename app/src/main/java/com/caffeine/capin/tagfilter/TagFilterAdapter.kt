package com.caffeine.capin.tagfilter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.caffeine.capin.BR
import com.caffeine.capin.databinding.ItemTagFilterBinding

class TagFilterAdapter(private val listener: FilterClickListener): RecyclerView.Adapter<TagFilterAdapter.TagFilterViewHolder>() {
    var checkTagList = arrayListOf<TagFilterEntity?>()

    interface FilterClickListener {
        fun selectFilter(checkbox: CompoundButton, tag: TagFilterEntity)
        fun unSelectFilter(checkbox: CompoundButton, tag: TagFilterEntity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagFilterViewHolder {
        val binding = ItemTagFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TagFilterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TagFilterViewHolder, position: Int) {
        holder.binding.setVariable(BR.data, DATA[position])

        if(!checkTagList.isNullOrEmpty()){
            checkTagList.forEach { checkedTag ->
                if (checkedTag?.tagIndex == position) {
                    holder.binding.checkboxTagFilter.isChecked = true
                }
            }

        }

        holder.binding.checkboxTagFilter.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                listener.selectFilter(buttonView!!, DATA[position])
            } else {
                listener.unSelectFilter(buttonView!!, DATA[position])
            }
        }
    }

    override fun getItemCount() = DATA.size

    companion object {
        private val DATA: List<TagFilterEntity> = listOf(
            TagFilterEntity(
                "커피 맛집",
                0
            ),
            TagFilterEntity(
                "디저트 맛집",
                1
            ),
            TagFilterEntity(
                "브런치 카페",
                2
            ),
            TagFilterEntity(
                "작업하기 좋은",
                3
            ),
            TagFilterEntity(
                "산미없는 커피",
                4
            ),
            TagFilterEntity(
                "산미있는 커피",
                5
            )
        )
    }

    class TagFilterViewHolder(val binding: ItemTagFilterBinding): RecyclerView.ViewHolder(binding.root)
}