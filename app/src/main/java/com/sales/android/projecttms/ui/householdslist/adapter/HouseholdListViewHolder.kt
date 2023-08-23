package com.sales.android.projecttms.ui.householdslist.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.sales.android.projecttms.databinding.ItemHouseholdBinding
import com.sales.android.projecttms.model.HouseholdData

class HouseholdListViewHolder(private val binding: ItemHouseholdBinding) :
    RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun bind(household: HouseholdData) {
        binding.run {
            numberHH.text = household.numberHH.toString() + "."
            if (household.openStatus == true) {
                openStatus.text = "Opened"
                openStatus.setTextColor(Color.parseColor("#13803e"))
            } else {
                openStatus.text = "Closed"
                openStatus.setTextColor(Color.parseColor("#d4482c"))

            }
            statusOfHousehold.text = household.statusOfHouseHold.toString() ?: ""
            reasonForStatus.text = household.reasonForStatus.toString() ?: ""
            if (household.contact != null) {
                name.text = household.contact.name
                if ((household.contact.fixedProvider != "") && (household.contact.mobileProvider != "")) {
                    provider.text =
                        household.contact.fixedProvider.toString() + "/" + household.contact.mobileProvider.toString()
                }
                if (household.contact.totalPayment != 0) {
                    payment.text = household.contact.totalPayment.toString()
                }
                comments.text = household.contact.comments
            } else {
                ""
            }
        }
    }
}