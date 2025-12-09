package com.cybershield.ui.blockchain.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cybershield.blockchain.Block
import com.cybershield.databinding.ItemBlockBinding
import java.text.SimpleDateFormat
import java.util.*

class BlockAdapter(private val blocks: List<Block>) : RecyclerView.Adapter<BlockAdapter.BlockViewHolder>() {

    private val dateFormat = SimpleDateFormat("dd MMM yyyy, HH:mm:ss", Locale.getDefault())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockViewHolder {
        val binding = ItemBlockBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BlockViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BlockViewHolder, position: Int) {
        holder.bind(blocks[position])
    }

    override fun getItemCount() = blocks.size

    inner class BlockViewHolder(private val binding: ItemBlockBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(block: Block) {
            binding.blockIndex.text = "Block #${block.index}"
            binding.blockTimestamp.text = dateFormat.format(Date(block.timestamp))
            binding.blockSender.text = "From: ${block.data.sender}"
            binding.blockReceiver.text = "To: ${block.data.receiver}"
            binding.blockAmount.text = "Amount: ₹${block.data.amount}"
            binding.blockHash.text = "Hash: ${block.hash.take(16)}..."
            binding.blockPreviousHash.text = "Prev: ${block.previousHash.take(16)}..."
            if (block.data.description.isNotEmpty()) {
                binding.blockDescription.text = block.data.description
                binding.blockDescription.visibility = android.view.View.VISIBLE
            } else {
                binding.blockDescription.visibility = android.view.View.GONE
            }
        }
    }
}


