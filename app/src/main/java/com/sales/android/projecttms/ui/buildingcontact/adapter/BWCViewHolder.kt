package com.sales.android.projecttms.ui.buildingcontact.adapter

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.sales.android.projecttms.databinding.ItemBuildingWithContactsBinding
import com.sales.android.projecttms.model.BuildingData
import com.sales.android.projecttms.utils.convertLongToTime
import java.time.Instant.now
import java.time.LocalDate
import java.time.LocalDate.now
import java.time.MonthDay.now
import java.util.Date

class BWCViewHolder(private val binding: ItemBuildingWithContactsBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(building: BuildingData) {
        var counterContacts = 0
        var counterTodayContacts = 0
        for (hh in building.houseHoldsList) {
            if (hh.contact.phoneNumber != "") {
                counterContacts += 1
                val currentDate = Date().time
                if (hh.contact.dateOfNextContact == convertLongToTime(currentDate)) counterTodayContacts +=1
            }
        }
        binding.run {
            numberFactHH.text = counterContacts.toString()
            street.text = building.buildingStreet
            houseNumber.text = building.houseNumber.toString()
            houseCorpus.text = building.houseCorpus ?: ""
            toCallFactHH.text = counterTodayContacts.toString()
        }
    }
}


