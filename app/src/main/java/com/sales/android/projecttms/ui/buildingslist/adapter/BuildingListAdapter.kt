package com.sales.android.projecttms.ui.buildingslist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.sales.android.projecttms.databinding.ItemBuildingBinding
import com.sales.android.projecttms.model.BuildingData

class BuildingListAdapter (
    private val onPopupClicked: (buildingId: Int, itemView: View) -> Unit,
) : ListAdapter<BuildingData, BuildingListViewHolder>(
    object : DiffUtil.ItemCallback<BuildingData>() {
        override fun areItemsTheSame(oldItem: BuildingData, newItem: BuildingData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: BuildingData, newItem: BuildingData): Boolean {
            return oldItem.houseHoldsList == newItem.houseHoldsList
        }

    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuildingListViewHolder {
        return BuildingListViewHolder(
            ItemBuildingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BuildingListViewHolder, position: Int) {
        holder.bindFB(getItem(position), onPopupClicked)

    }
}