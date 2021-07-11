package com.caffeine.capin.mypage.pin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.caffeine.capin.databinding.ItemCategoryPinDetailBinding
import com.caffeine.capin.mypage.archivingcategory.ArchivingCategory

class PinInfoAdapter : RecyclerView.Adapter<PinInfoAdapter.PinInfoViewHolder>() {

    val pinInfoList = mutableListOf<PinInfo>()
    private var isVisible: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PinInfoViewHolder {
        val binding = ItemCategoryPinDetailBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PinInfoViewHolder(binding)
    }

    override fun getItemCount(): Int = pinInfoList.size

    override fun onBindViewHolder(holder: PinInfoViewHolder, position: Int) {
        holder.onBind(pinInfoList[position])

        holder.binding.pinDetailChoiceCheckbox.visibility =
            if (isVisible) View.VISIBLE else View.GONE

        val pinInfoTagAdapter = PinInfoTagAdapter()
        holder.binding.pinDetailTagRcv.adapter = pinInfoTagAdapter
        pinInfoTagAdapter.pinInfoTagList.addAll(
            pinInfoList[position].tags
        )
        pinInfoTagAdapter.notifyDataSetChanged()
    }

    fun switchDeleteMode(mode: Boolean) {
        this.isVisible = mode
        notifyDataSetChanged()
    }

    interface OnCheckboxClickListener {
        fun onCheckboxClick(pinInfo: PinInfo)
    }

    private lateinit var checkboxClickListener: OnCheckboxClickListener

    fun setOnCheckboxClickListener(listener: OnCheckboxClickListener) {
        this.checkboxClickListener = listener
    }

    inner class PinInfoViewHolder(
        val binding: ItemCategoryPinDetailBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(pinInfo: PinInfo) {
            binding.pinDetailNameTv.text = pinInfo.name
            binding.pinDetailAddressTv.text = pinInfo.address

            binding.pinDetailChoiceCheckbox.setOnClickListener {
                checkboxClickListener.onCheckboxClick(pinInfo)
            }
        }
    }
}