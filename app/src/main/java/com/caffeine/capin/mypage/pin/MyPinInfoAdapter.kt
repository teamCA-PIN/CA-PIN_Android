package com.caffeine.capin.mypage.pin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.caffeine.capin.databinding.ItemCategoryPinDetailBinding

class MyPinInfoAdapter : RecyclerView.Adapter<MyPinInfoAdapter.MyPinInfoViewHolder>() {

    val myPinInfoList = mutableListOf<MyPinInfo>()
    private var isVisible: Boolean = false
    var checkboxList = mutableListOf<CheckBox>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPinInfoViewHolder {
        val binding = ItemCategoryPinDetailBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyPinInfoViewHolder(binding)
    }

    override fun getItemCount(): Int = myPinInfoList.size

    override fun onBindViewHolder(holder: MyPinInfoViewHolder, position: Int) {
        holder.onBind(myPinInfoList[position])

        holder.binding.pinDetailChoiceCheckbox.visibility =
            if (isVisible) View.VISIBLE else View.GONE

        val pinInfoTagAdapter = PinInfoTagAdapter()
        holder.binding.pinDetailTagRcv.adapter = pinInfoTagAdapter
        pinInfoTagAdapter.pinInfoTagList.addAll(
            myPinInfoList[position].tags
        )
        pinInfoTagAdapter.notifyDataSetChanged()
    }

    fun switchDeleteMode(mode: Boolean) {
        this.isVisible = mode
        notifyDataSetChanged()
    }

    interface OnCheckboxClickListener {
        fun onCheckboxClick(myPinInfo: MyPinInfo)
    }

    private lateinit var checkboxClickListener: OnCheckboxClickListener

    fun setOnCheckboxClickListener(listener: OnCheckboxClickListener) {
        this.checkboxClickListener = listener
    }

    inner class MyPinInfoViewHolder(
        val binding: ItemCategoryPinDetailBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(myPinInfo: MyPinInfo) {
            binding.pinDetailNameTv.text = myPinInfo.name
            binding.pinDetailAddressTv.text = myPinInfo.address

            binding.pinDetailChoiceCheckbox.setOnClickListener {
                checkboxClickListener.onCheckboxClick(myPinInfo)
                if (binding.pinDetailChoiceCheckbox.isChecked) {
                    checkboxList.add(binding.pinDetailChoiceCheckbox)
                } else {
                    checkboxList.remove(binding.pinDetailChoiceCheckbox)
                }
            }
        }
    }
}