package com.cybershield.ui.loans.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cybershield.data.model.Loan
import com.cybershield.databinding.ItemLoanBinding

class LoanAdapter(
    private val loans: List<Loan>,
    private val onItemClick: (Loan) -> Unit
) : RecyclerView.Adapter<LoanAdapter.LoanViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoanViewHolder {
        val binding = ItemLoanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoanViewHolder, position: Int) {
        holder.bind(loans[position])
    }

    override fun getItemCount() = loans.size

    inner class LoanViewHolder(private val binding: ItemLoanBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loan: Loan) {
            binding.loanName.text = loan.name
            binding.loanDescription.text = loan.description
            binding.interestRate.text = loan.interestRate
            binding.root.setOnClickListener { onItemClick(loan) }
        }
    }
}

