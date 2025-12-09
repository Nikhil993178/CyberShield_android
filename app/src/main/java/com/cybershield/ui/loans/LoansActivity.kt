package com.cybershield.ui.loans

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.cybershield.data.DummyData
import com.cybershield.databinding.ActivityLoansBinding
import com.cybershield.ui.loans.adapter.LoanAdapter

class LoansActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoansBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoansBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Loans"

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val loans = DummyData.getDummyLoans()
        val adapter = LoanAdapter(loans) { loan ->
            Toast.makeText(this, "${loan.name}\n${loan.description}\nInterest Rate: ${loan.interestRate}", Toast.LENGTH_LONG).show()
        }
        binding.loansRecyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.loansRecyclerView.adapter = adapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

