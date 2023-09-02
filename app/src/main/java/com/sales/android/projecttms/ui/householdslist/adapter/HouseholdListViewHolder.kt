package com.sales.android.projecttms.ui.householdslist.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.sales.android.projecttms.databinding.ItemHouseholdBinding
import com.sales.android.projecttms.model.HouseholdData

class HouseholdListViewHolder(private val binding: ItemHouseholdBinding) :
    RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun bind(household: HouseholdData, onItemClick: (household:HouseholdData) -> Unit) {
        binding.run {
            numberHH.text = household.numberHH.toString() + "."
            if (household.connectedStatus) {
                cardView.isClickable = false
                openStatus.text = "A1"
                openStatus.setTextColor(Color.parseColor("#13803e"))
            } else {
                if (household.openStatus) {
                    openStatus.text = "Opened"
                    openStatus.setTextColor(Color.parseColor("#13803e"))
                } else {
                    openStatus.text = "Closed"
                    openStatus.setTextColor(Color.parseColor("#d4482c"))

                }
                statusOfHousehold.text = household.statusOfHouseHold
                reasonForStatus.text = household.reasonForStatus
                run {
                    name.text = household.contact.name
                    if ((household.contact.fixedProvider != "") && (household.contact.mobileProvider != "")) {
                        provider.text =
                            household.contact.fixedProvider + "/" + household.contact.mobileProvider
                    }
                    if (household.contact.totalPayment != 0) {
                        payment.text = household.contact.totalPayment.toString()
                    }
                    comments.text = household.contact.comments
                    itemView.setOnClickListener{
                        onItemClick(household)
                    }
                }
            }
        }
    }
}