package com.caffeine.capin.review

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.caffeine.capin.BR
import com.caffeine.capin.databinding.ItemPinDetailTagBinding
import com.caffeine.capin.databinding.ItemReviewTagBinding
import com.caffeine.capin.util.DiffCallback

class ReviewTagAdapter(private val viewType: Int): ListAdapter<String, RecyclerView.ViewHolder>(
    DiffCallback<String>()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == 1) {
            val binding =ItemPinDetailTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ReviewSecondTypeViewHolder(binding)
        } else {
            val binding = ItemReviewTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ReviewTagViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val tag = getItem(position)
        if (holder is ReviewSecondTypeViewHolder) {
            holder.binding.pinDetailTagTv.text = tag
        } else {
            (holder as ReviewTagViewHolder).binding.setVariable(BR.tag, tag)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return viewType
    }

    class ReviewTagViewHolder(val binding: ItemReviewTagBinding): RecyclerView.ViewHolder(binding.root)
    class ReviewSecondTypeViewHolder(val binding: ItemPinDetailTagBinding): RecyclerView.ViewHolder(binding.root)
}