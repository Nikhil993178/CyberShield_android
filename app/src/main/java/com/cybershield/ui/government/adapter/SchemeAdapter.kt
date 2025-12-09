package com.cybershield.ui.government.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cybershield.data.GovernmentScheme
import com.cybershield.databinding.ItemSchemeBinding

class SchemeAdapter(
    private val schemes: List<GovernmentScheme>,
    private val onItemClick: (GovernmentScheme) -> Unit
) : RecyclerView.Adapter<SchemeAdapter.SchemeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchemeViewHolder {
        val binding = ItemSchemeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SchemeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SchemeViewHolder, position: Int) {
        holder.bind(schemes[position])
    }

    override fun getItemCount() = schemes.size

    inner class SchemeViewHolder(private val binding: ItemSchemeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(scheme: GovernmentScheme) {
            binding.schemeName.text = scheme.name
            binding.schemeDescription.text = scheme.description
            binding.viewDetailsButton.setOnClickListener { onItemClick(scheme) }
        }
    }
}

