package com.caffeine.capin.presentation.map

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.caffeine.capin.databinding.ItemPinDetailTagBinding
import com.caffeine.capin.presentation.util.DiffCallback

class CafeTagListAdapter: ListAdapter<String, CafeTagListAdapter.CafeTagListViewHolder>(
    DiffCallback<String>()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CafeTagListViewHolder {
        val binding = ItemPinDetailTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CafeTagListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CafeTagListViewHolder, position: Int) {
        val tag = getItem(position)
        holder.onBind(tag)
    }

    class CafeTagListViewHolder(val binding: ItemPinDetailTagBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(tag: String) { binding.pinDetailTagTv.text = tag }
    }
}