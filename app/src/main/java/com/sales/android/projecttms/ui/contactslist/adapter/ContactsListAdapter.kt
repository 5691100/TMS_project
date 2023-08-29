package com.sales.android.projecttms.ui.contactslist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.sales.android.projecttms.databinding.ItemContactBinding
import com.sales.android.projecttms.model.BuildingData
import com.sales.android.projecttms.model.ContactData

class ContactsListAdapter(
    private val onItemClick: (contact: ContactData, view: View) -> Unit
) : ListAdapter<ContactData, ContactsListViewHolder>(
    object : DiffUtil.ItemCallback<ContactData>() {
        override fun areItemsTheSame(oldItem: ContactData, newItem: ContactData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ContactData, newItem: ContactData): Boolean {
            return oldItem.dateOfNextContact == newItem.dateOfNextContact
        }
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsListViewHolder {
        return ContactsListViewHolder(
            ItemContactBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ContactsListViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener{
            onItemClick(getItem(position), it)
        }
    }

}