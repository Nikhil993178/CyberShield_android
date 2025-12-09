package com.cybershield.ui.investments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cybershield.databinding.ActivityInvestmentsBinding

class InvestmentsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInvestmentsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInvestmentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Investments & Savings"

        setupData()
    }

    private fun setupData() {
        binding.savingsRate.text = "3.5% p.a."
        binding.fd3Months.text = "5.0% p.a."
        binding.fd6Months.text = "5.5% p.a."
        binding.fd1Year.text = "6.0% p.a."
        binding.rdRate.text = "5.5% p.a."
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

