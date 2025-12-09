package com.cybershield.ui.government

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cybershield.data.DummyData
import com.cybershield.databinding.ActivityGovernmentSchemesBinding
import com.cybershield.ui.government.adapter.SchemeAdapter

class GovernmentSchemesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGovernmentSchemesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGovernmentSchemesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Government Schemes"

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val schemes = DummyData.getDummySchemes()
        val adapter = SchemeAdapter(schemes) { scheme ->
            Toast.makeText(this, "${scheme.name}\n\n${scheme.description}", Toast.LENGTH_LONG).show()
        }
        binding.schemesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.schemesRecyclerView.adapter = adapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

