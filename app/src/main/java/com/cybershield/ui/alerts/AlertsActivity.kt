package com.cybershield.ui.alerts

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cybershield.databinding.ActivityAlertsBinding

class AlertsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAlertsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlertsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.freezeButton.setOnClickListener {
            Toast.makeText(this, "Freeze requested (placeholder)", Toast.LENGTH_SHORT).show()
        }
        binding.reportButton.setOnClickListener {
            Toast.makeText(this, "Report submitted (placeholder)", Toast.LENGTH_SHORT).show()
        }
    }
}

