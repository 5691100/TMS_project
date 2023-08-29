package com.sales.android.projecttms.ui.buildingcontact.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.sales.android.projecttms.databinding.ItemBuildingWithContactsBinding
import com.sales.android.projecttms.databinding.ItemHouseholdBinding
import com.sales.android.projecttms.model.BuildingData
import com.sales.android.projecttms.model.HouseholdData
import com.sales.android.projecttms.ui.householdslist.adapter.HouseholdListViewHolder

class BWCAdapter(
    private val onItemClick: (building: BuildingData) -> Unit
) : ListAdapter<BuildingData, BWCViewHolder>(
    object : DiffUtil.ItemCallback<BuildingData>() {
        override fun areItemsTheSame(oldItem: BuildingData, newItem: BuildingData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: BuildingData, newItem: BuildingData): Boolean {
            return oldItem.houseHoldsList == newItem.houseHoldsList
        }
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BWCViewHolder {
        return BWCViewHolder(
            ItemBuildingWithContactsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BWCViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            onItemClick(getItem(position))
        }
    }
}
