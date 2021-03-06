package com.caffeine.capin.presentation.review.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.caffeine.capin.databinding.ItemCafeReviewBinding
import com.caffeine.capin.data.dto.review.CafeReview
import com.caffeine.capin.presentation.util.DiffCallback
import com.caffeine.capin.presentation.util.HorizontalItemDecoration

class CafeReviewsAdapter(private val listener: ExpandImageInterface) : ListAdapter<CafeReview, CafeReviewsAdapter.ViewHolder>(
    DiffCallback<CafeReview>()
) {
    interface ExpandImageInterface {
        fun expand(images: List<String>)
        fun edit(review: CafeReview)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCafeReviewBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        setTags(holder)
        expandImages(holder)
        editReview(holder)
    }

    private fun setTags(holder: ViewHolder) {
        val review = getItem(holder.absoluteAdapterPosition)
        holder.binding.recyclerviewTag.run {
            adapter = ReviewTagAdapter(2)
            addItemDecoration(HorizontalItemDecoration(4))
            (adapter as ReviewTagAdapter).submitList(review.getRecommendStrings())
        }
    }

    private fun expandImages(holder: ViewHolder) {
        val review = getItem(holder.absoluteAdapterPosition)
        holder.binding.run {
            val images = listOf(imageviewReviewFirst, imageviewReviewSecond, imageviewReviewThird)
            images.forEach {
                it.setOnClickListener { listener.expand(review.imageUrls) }
            }
        }
    }

    private fun editReview(holder: ViewHolder) {
        val review = getItem(holder.absoluteAdapterPosition)
        holder.binding.imageviewEdit.setOnClickListener {
            listener.edit(review)
        }
    }

    inner class ViewHolder(val binding: ItemCafeReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(review: CafeReview) {
            binding.review = review
        }
    }
}
