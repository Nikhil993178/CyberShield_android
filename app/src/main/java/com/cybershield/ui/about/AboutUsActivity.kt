package com.cybershield.ui.about

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cybershield.databinding.ActivityAboutUsBinding

class AboutUsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutUsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutUsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "About Us"

        binding.bankName.text = "Cyber Shield Bank"
        binding.branchName.text = "Main Branch - New Delhi"
        binding.ifscCode.text = "CSHB0000123"
        binding.customerCare.text = "+91-1800-123-4567"
        binding.email.text = "support@cybershieldbank.com"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

