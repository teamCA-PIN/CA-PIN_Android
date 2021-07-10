package com.caffeine.capin.tagfilter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.caffeine.capin.BR
import com.caffeine.capin.databinding.ItemTagFilterBinding

class TagFilterAdapter(val listener: FilterClickListener): RecyclerView.Adapter<TagFilterAdapter.TagFilterViewHolder>() {

    interface FilterClickListener {
        fun selectFilter(checkbox: CompoundButton)
        fun unSelectFilter(checkbox: CompoundButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagFilterViewHolder {
        val binding = ItemTagFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TagFilterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TagFilterViewHolder, position: Int) {
        holder.binding.setVariable(BR.data, DATA[position])

        holder.binding.checkboxTagFilter.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if (isChecked) {
                    listener.selectFilter(buttonView!!)
                } else {
                    listener.unSelectFilter(buttonView!!)
                }
            }
        })
    }

    override fun getItemCount() = DATA.size

    companion object {
        private val DATA: List<TagFilterEntity> = listOf(
            TagFilterEntity(
                "커피 맛집"
            ),
            TagFilterEntity(
                "디저트 맛집"
            ),
            TagFilterEntity(
                "브런치 카페"
            ),
            TagFilterEntity(
                "작업하기 좋은"
            ),
            TagFilterEntity(
                "산미없는 커피"
            ),
            TagFilterEntity(
                "산미있는 커피"
            )
        )
    }

    class TagFilterViewHolder(val binding: ItemTagFilterBinding): RecyclerView.ViewHolder(binding.root)
}