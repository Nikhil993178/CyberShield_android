package com.cybershield.ui.transaction

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cybershield.databinding.ActivityTransactionProcessBinding

class TransactionProcessActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransactionProcessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionProcessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Transaction Process"

        setupButtons()
    }

    private fun setupButtons() {
        binding.netBankingCard.setOnClickListener {
            showNetBankingSteps()
        }

        binding.upiCard.setOnClickListener {
            showUpiSteps()
        }
    }

    private fun showNetBankingSteps() {
        val steps = """
            1. Login to Net Banking
            2. Navigate to Funds Transfer
            3. Add Beneficiary (if not added)
            4. Enter beneficiary details and verify
            5. Select beneficiary and enter amount
            6. Review transaction details
            7. Enter OTP sent to registered mobile
            8. Confirm transaction
            9. Transaction successful
        """.trimIndent()

        android.app.AlertDialog.Builder(this)
            .setTitle("Net Banking Transaction Steps")
            .setMessage(steps)
            .setPositiveButton("OK", null)
            .show()
    }

    private fun showUpiSteps() {
        val steps = """
            1. Open UPI app
            2. Select Send Money
            3. Choose contact or enter UPI ID
            4. Verify receiver name carefully
            5. Enter amount
            6. Add note (optional)
            7. Enter UPI PIN
            8. Transaction successful
            
            ⚠️ Security Tips:
            - Always verify receiver name
            - Never share UPI PIN
            - Check amount before confirming
            - Report suspicious transactions immediately
        """.trimIndent()

        android.app.AlertDialog.Builder(this)
            .setTitle("UPI Transaction Steps")
            .setMessage(steps)
            .setPositiveButton("OK", null)
            .show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

