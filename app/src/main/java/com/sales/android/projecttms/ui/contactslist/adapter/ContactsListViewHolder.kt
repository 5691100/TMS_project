package com.sales.android.projecttms.ui.contactslist.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.sales.android.projecttms.databinding.ItemContactBinding
import com.sales.android.projecttms.model.ContactData
import com.sales.android.projecttms.utils.convertLongToTime
import java.util.Date

class ContactsListViewHolder(private val binding: ItemContactBinding) :
    RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(contact: ContactData) {
            binding.run {
                numberHH.text = contact.numberHH.toString()
                name.text = contact.name
                phoneNumber.text = contact.phoneNumber
                dateOfNextContact.text = contact.dateOfNextContact
                if (contact.dateOfNextContact == convertLongToTime(Date().time)) {
                    itemView.setBackgroundColor(Color.parseColor("#d4482c"))
                }
                if ((contact.fixedProvider != "") && (contact.mobileProvider != "")) {
                    provider.text =
                        contact.fixedProvider + "/" + contact.mobileProvider
                }
                if (contact.totalPayment != 0) {
                    payment.text = contact.totalPayment.toString()
                }
                openStatus.text = contact.statusOfContact
                comments.text = contact.comments
            }
        }
}