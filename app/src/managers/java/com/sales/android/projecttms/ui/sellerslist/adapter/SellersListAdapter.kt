package com.sales.android.projecttms.ui.sellerslist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.sales.android.projecttms.databinding.ItemSellerBinding
import com.sales.android.projecttms.model.Seller

class SellersListAdapter(
    private val onItemClick: (seller: Seller) -> Unit
) : ListAdapter<Seller, SellersListVIewHolder>(
    object : DiffUtil.ItemCallback<Seller>() {
        override fun areItemsTheSame(oldItem: Seller, newItem: Seller): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: Seller, newItem: Seller): Boolean {
            return false
        }

    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SellersListVIewHolder {
        return SellersListVIewHolder(
            ItemSellerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: SellersListVIewHolder, position: Int) {
        holder.bind(getItem(position), onItemClick)
    }
}