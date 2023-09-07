package com.sales.android.projecttms.ui.sellerslist.adapter

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.sales.android.projecttms.databinding.ItemSellerBinding
import com.sales.android.projecttms.model.Seller

class SellersListVIewHolder(private val binding: ItemSellerBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        seller: Seller,
        onItemClick: (seller: Seller) -> Unit
    ) {
        binding.run {
            nameSeller.text = seller.firstName
            lastNameSeller.text = seller.lastName
            if (seller.work == 1) {
                isOnWorkStatus.text = "На работе"
                isOnWorkStatus.setTextColor(Color.parseColor("#13803e"))
            } else {
                isOnWorkStatus.text = "Не на работе"
                isOnWorkStatus.setTextColor(Color.parseColor("#d4482c"))
            }
            itemView.setOnClickListener {
                onItemClick(seller)
            }
        }
    }
}