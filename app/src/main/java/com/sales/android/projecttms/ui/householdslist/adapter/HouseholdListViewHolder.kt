package com.sales.android.projecttms.ui.householdslist.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.sales.android.projecttms.databinding.ItemHouseholdBinding
import com.sales.android.projecttms.model.HouseholdData

class HouseholdListViewHolder(private val binding: ItemHouseholdBinding) :
    RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun bind(household: HouseholdData) {
        binding.run {
            numberHH.text = household.numberHH.toString() + "."
            openStatus.text = if (household.openStatus == true) {
                "Opened"
            } else {
                "Closed"
            }
            statusOfHousehold.text = household.statusOfHouseHold.toString() ?: ""
            reasonForStatus.text = household.reasonForStatus.toString() ?: ""
            if (household.contact != null) {
                name.text = household.contact.name
                provider.text =
                    household.contact.fixedProvider.toString() + "/" + household.contact.mobileProvider.toString()
                payment.text = household.contact.totalPayment.toString()
                comments.text = household.contact.comments
            } else {
                ""
            }
        }
    }
}