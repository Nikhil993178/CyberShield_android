package com.cybershield.ui.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cybershield.data.model.BankOffer
import com.cybershield.databinding.ItemOfferBinding

class OfferAdapter(
    private val offers: List<BankOffer>
) : RecyclerView.Adapter<OfferAdapter.OfferViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val binding = ItemOfferBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OfferViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        holder.bind(offers[position])
    }

    override fun getItemCount() = offers.size

    inner class OfferViewHolder(private val binding: ItemOfferBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(offer: BankOffer) {
            binding.offerTitle.text = offer.title
            binding.offerDescription.text = offer.description
        }
    }
}


