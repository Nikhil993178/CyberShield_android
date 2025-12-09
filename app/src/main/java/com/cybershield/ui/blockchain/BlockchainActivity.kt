package com.cybershield.ui.blockchain

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cybershield.blockchain.BlockchainLedger
import com.cybershield.blockchain.TransactionData
import com.cybershield.databinding.ActivityBlockchainBinding
import com.cybershield.ui.blockchain.adapter.BlockAdapter
import com.cybershield.ui.transaction.TransactionHistoryActivity

class BlockchainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBlockchainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBlockchainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        BlockchainLedger.initialize(this)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Blockchain Banking"

        setupRecyclerView()
        setupSendButton()
        checkChainValidity()
    }

    private fun setupRecyclerView() {
        val blocks = BlockchainLedger.getChain()
        val adapter = BlockAdapter(blocks)
        binding.blocksRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.blocksRecyclerView.adapter = adapter
    }

    private fun setupSendButton() {
        binding.sendTransactionButton.setOnClickListener {
            val receiver = binding.receiverEdit.text.toString().trim()
            val amountText = binding.amountEdit.text.toString().trim()
            val description = binding.descriptionEdit.text.toString().trim()

            if (receiver.isEmpty() || amountText.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                val amount = amountText.toDouble()
                if (amount <= 0) {
                    Toast.makeText(this, "Amount must be greater than 0", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val transactionData = TransactionData(
                    sender = "USER_ACCOUNT",
                    receiver = receiver,
                    amount = amount,
                    description = description.ifEmpty { "Blockchain Transaction" }
                )

                val newBlock = BlockchainLedger.addTransaction(this, transactionData)
                Toast.makeText(this, "Transaction added to blockchain!\nBlock Hash: ${newBlock.hash.take(16)}...", Toast.LENGTH_LONG).show()

                // Clear inputs
                binding.receiverEdit.text?.clear()
                binding.amountEdit.text?.clear()
                binding.descriptionEdit.text?.clear()

                // Refresh list
                setupRecyclerView()

                // Update transaction history
                val intent = Intent(this, TransactionHistoryActivity::class.java)
                intent.putExtra("refresh", true)
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Invalid amount", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkChainValidity() {
        val isValid = BlockchainLedger.isChainValid()
        binding.chainStatusText.text = if (isValid) {
            "✓ Blockchain is valid"
        } else {
            "✗ Blockchain integrity compromised"
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}


