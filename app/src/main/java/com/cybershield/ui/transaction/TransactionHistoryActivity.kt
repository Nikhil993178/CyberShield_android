package com.cybershield.ui.transaction

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cybershield.data.DummyData
import com.cybershield.databinding.ActivityTransactionHistoryBinding
import com.cybershield.ui.transaction.adapter.TransactionAdapter
import com.cybershield.blockchain.BlockchainLedger
import com.cybershield.data.model.Transaction
import com.cybershield.data.model.TransactionType
import java.util.*

class TransactionHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransactionHistoryBinding
    private var selectedDate: Calendar = Calendar.getInstance()

    override fun onResume() {
        super.onResume()
        // Refresh transactions when returning from blockchain screen
        filterTransactions()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        BlockchainLedger.initialize(this)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Transaction History"

        setupDatePicker()
        setupRecyclerView()
    }

    private fun setupDatePicker() {
        binding.datePickerButton.setOnClickListener {
            val year = selectedDate.get(Calendar.YEAR)
            val month = selectedDate.get(Calendar.MONTH)
            val day = selectedDate.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                selectedDate.set(selectedYear, selectedMonth, selectedDay)
                updateDateDisplay()
                filterTransactions()
            }, year, month, day).show()
        }
        updateDateDisplay()
    }

    private fun updateDateDisplay() {
        val format = java.text.SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        binding.datePickerButton.text = "Date: ${format.format(selectedDate.time)}"
    }

    private fun setupRecyclerView() {
        filterTransactions()
    }

    private fun filterTransactions() {
        // Get transactions from blockchain
        val blockchainBlocks = BlockchainLedger.getChain()
        val blockchainTransactions = blockchainBlocks.mapIndexed { index, block ->
            Transaction(
                id = "BLOCK_${block.index}",
                date = Date(block.timestamp),
                description = block.data.description.ifEmpty { "Blockchain Transaction: ${block.data.sender} → ${block.data.receiver}" },
                type = if (block.data.sender == "USER_ACCOUNT") TransactionType.DEBIT else TransactionType.CREDIT,
                amount = block.data.amount,
                runningBalance = 50000.0 - (blockchainBlocks.take(index + 1).sumOf { it.data.amount.toLong() })
            )
        }.filter { it.id != "BLOCK_0" } // Exclude genesis block

        // Merge with dummy transactions
        val dummyTransactions = DummyData.getDummyTransactions()
        val allTransactions = (blockchainTransactions + dummyTransactions).sortedByDescending { it.date }

        // Filter by selected date if needed
        val filtered = if (intent.getBooleanExtra("refresh", false)) {
            allTransactions
        } else {
            allTransactions // For now, show all. Can add date filtering later
        }

        val adapter = TransactionAdapter(filtered)
        binding.transactionsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.transactionsRecyclerView.adapter = adapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

