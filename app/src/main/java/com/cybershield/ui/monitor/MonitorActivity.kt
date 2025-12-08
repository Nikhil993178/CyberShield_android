package com.cybershield.ui.monitor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cybershield.databinding.ActivityMonitorBinding

class MonitorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMonitorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMonitorBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}

