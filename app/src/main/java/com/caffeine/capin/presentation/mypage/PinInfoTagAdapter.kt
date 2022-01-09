package com.caffeine.capin.presentation.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.caffeine.capin.databinding.ItemPinDetailTagBinding
import com.caffeine.capin.data.dto.mypage.PinInfoTag

class PinInfoTagAdapter : RecyclerView.Adapter<PinInfoTagAdapter.PinInfoTagViewHolder>() {

    val pinInfoTagList = mutableListOf<PinInfoTag>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PinInfoTagViewHolder {
        val binding = ItemPinDetailTagBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PinInfoTagViewHolder(binding)
    }

    override fun getItemCount(): Int = pinInfoTagList.size

    override fun onBindViewHolder(holder: PinInfoTagViewHolder, position: Int) {
        holder.onBind(pinInfoTagList[position])
    }

    class PinInfoTagViewHolder(
        val binding: ItemPinDetailTagBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(pinInfoTag: PinInfoTag) {
            binding.pinDetailTagTv.text = pinInfoTag.name
        }
    }
}