package com.sales.android.projecttms.ui.buildingcontact.adapter

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.sales.android.projecttms.databinding.ItemBuildingWithContactsBinding
import com.sales.android.projecttms.model.BuildingData
import com.sales.android.projecttms.model.StatusOfContact
import com.sales.android.projecttms.utils.convertLongToTime
import com.sales.android.projecttms.utils.convertTimeToLong
import java.util.Date

class BWCViewHolder(private val binding: ItemBuildingWithContactsBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(building: BuildingData) {
        var counterContacts = 0
        var counterTodayContacts = 0
        var expireContact = 0
        for (hh in building.houseHoldsList) {
            if (hh.contact.phoneNumber != "") {
                counterContacts += 1
                val currentDate = Date().time
                if (hh.contact.dateOfNextContact == convertLongToTime(currentDate)) counterTodayContacts += 1
                if (hh.contact.statusOfContact != StatusOfContact.REFUSE.status && convertTimeToLong(
                        hh.contact.dateOfNextContact
                    ) < convertTimeToLong(
                        convertLongToTime(Date().time)
                    )
                )
                    expireContact += 1
            }
        }
        binding.run {
            numberFactHH.text = counterContacts.toString()
            street.text = building.buildingStreet
            houseNumber.text = building.houseNumber.toString()
            houseCorpus.text = building.houseCorpus
            toCallFactHH.text = counterTodayContacts.toString()
            expireFact.text = expireContact.toString()
            if (expireContact > 0) expireFact.setTextColor(Color.parseColor("#d4482c"))
        }
    }
}


