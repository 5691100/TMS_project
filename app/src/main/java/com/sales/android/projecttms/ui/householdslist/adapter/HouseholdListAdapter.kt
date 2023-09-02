package com.sales.android.projecttms.ui.householdslist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.sales.android.projecttms.databinding.ItemHouseholdBinding
import com.sales.android.projecttms.model.HouseholdData

class HouseholdListAdapter(
    private val onItemClick: (household: HouseholdData) -> Unit
) : ListAdapter<HouseholdData, HouseholdListViewHolder>(
    object : DiffUtil.ItemCallback<HouseholdData>() {
        override fun areItemsTheSame(oldItem: HouseholdData, newItem: HouseholdData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: HouseholdData, newItem: HouseholdData): Boolean {
            return oldItem.statusOfHouseHold == newItem.statusOfHouseHold
        }
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HouseholdListViewHolder {
        return HouseholdListViewHolder(
            ItemHouseholdBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: HouseholdListViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClick)
//        holder.itemView.setOnClickListener {
//            onItemClick(getItem(position))
    }
}
