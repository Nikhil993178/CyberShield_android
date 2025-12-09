package com.cybershield.ui.transaction.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cybershield.data.model.Transaction
import com.cybershield.databinding.ItemTransactionBinding
import java.text.SimpleDateFormat
import java.util.*

class TransactionAdapter(private val transactions: List<Transaction>) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    private val dateFormat = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding = ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(transactions[position])
    }

    override fun getItemCount() = transactions.size

    inner class TransactionViewHolder(private val binding: ItemTransactionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction: Transaction) {
            binding.transactionDate.text = dateFormat.format(transaction.date)
            binding.transactionDescription.text = transaction.description
            binding.transactionType.text = transaction.type.name
            binding.transactionAmount.text = when (transaction.type) {
                com.cybershield.data.model.TransactionType.CREDIT -> "+₹${transaction.amount}"
                com.cybershield.data.model.TransactionType.DEBIT -> "-₹${transaction.amount}"
            }
            binding.runningBalance.text = "Balance: ₹${transaction.runningBalance}"
            
            binding.transactionAmount.setTextColor(
                when (transaction.type) {
                    com.cybershield.data.model.TransactionType.CREDIT -> 
                        binding.root.context.getColor(com.cybershield.R.color.primary)
                    com.cybershield.data.model.TransactionType.DEBIT -> 
                        binding.root.context.getColor(android.R.color.holo_red_dark)
                }
            )
        }
    }
}

