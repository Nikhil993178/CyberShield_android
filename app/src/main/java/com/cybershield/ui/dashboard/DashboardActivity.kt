package com.cybershield.ui.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cybershield.databinding.ActivityDashboardBinding
import com.cybershield.ui.alerts.AlertsActivity
import com.cybershield.ui.awareness.AwarenessActivity
import com.cybershield.ui.monitor.MonitorActivity
import com.cybershield.ui.settings.SettingsActivity

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.awarenessCard.setOnClickListener {
            startActivity(Intent(this, AwarenessActivity::class.java))
        }
        binding.alertsCard.setOnClickListener {
            startActivity(Intent(this, AlertsActivity::class.java))
        }
        binding.monitorCard.setOnClickListener {
            startActivity(Intent(this, MonitorActivity::class.java))
        }
        binding.settingsCard.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }
}

