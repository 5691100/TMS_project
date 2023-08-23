package com.sales.android.projecttms.ui.buildingslist.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sales.android.projecttms.databinding.ItemBuildingBinding
import com.sales.android.projecttms.model.BuildingData

class BuildingListViewHolder(private val binding: ItemBuildingBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindFB(
        building: BuildingData,
        onPopupClicked: (buildingId: Int, itemView: View) -> Unit,
    ) {
        var counter = 0
        for (hh in building.houseHoldsList) {
            if (hh.openStatus) counter += 1
        }
        binding.run {
            numberFactHH.text = building.totalHH.toString()
            street.text = building.buildingStreet
            houseNumber.text = building.houseNumber.toString()
            houseCorpus.text = building.houseCorpus ?: ""
            toOpenFactHH.text = (building.totalHH - counter).toString()
            moreActions.setOnClickListener {
                onPopupClicked(building.buildingID, itemView)
            }
        }
    }
}