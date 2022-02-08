package com.caffeine.capin.presentation.review.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.caffeine.capin.databinding.ItemReviewPictureBinding
import com.caffeine.capin.domain.entity.review.PictureUriEntity
import com.caffeine.capin.presentation.util.DiffCallback

class WriteReviewPictureAdapter(private val listener: DeleteListener) : ListAdapter<PictureUriEntity, WriteReviewPictureAdapter.WriteReviewPictureViewHolder>(
        DiffCallback<PictureUriEntity>()
    ) {
    interface DeleteListener{
        fun delete(pictureUriEntity: PictureUriEntity)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WriteReviewPictureViewHolder {
        val binding = ItemReviewPictureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WriteReviewPictureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WriteReviewPictureViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.binding.buttonDelete.setOnClickListener {
            listener.delete(item)
        }
    }

    class WriteReviewPictureViewHolder(val binding: ItemReviewPictureBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: PictureUriEntity) {
            binding.imageviewCafe.run {
                Glide.with(context).load(data.uri ?: data.url).into(this)
            }
        }
    }

}