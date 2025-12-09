package com.cybershield.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.cybershield.databinding.ActivityDashboardBinding
import com.cybershield.ui.about.AboutUsActivity
import com.cybershield.ui.auth.LoginActivity
import com.cybershield.ui.government.GovernmentSchemesActivity
import com.cybershield.ui.investments.InvestmentsActivity
import com.cybershield.ui.loans.LoansActivity
import com.cybershield.ui.news.NewsActivity
import com.cybershield.ui.pin.PinCreationActivity
import com.cybershield.ui.profile.ProfileActivity
import com.cybershield.ui.transaction.TransactionHistoryActivity
import com.cybershield.ui.transaction.TransactionProcessActivity
import com.cybershield.ui.alerts.AlertsActivity
import com.cybershield.ui.awareness.AwarenessActivity
import com.cybershield.ui.monitor.MonitorActivity
import com.cybershield.ui.settings.SettingsActivity
import com.cybershield.ui.dashboard.adapter.OfferAdapter
import com.cybershield.data.DummyData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView

class DashboardActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupNavigationDrawer()
        setupOffersCarousel()
        setupNavigationCards()
    }

    private fun setupOldCards() {
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

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(android.R.drawable.ic_menu_more)
        supportActionBar?.title = "Cyber Shield"

        // Profile icon click
        binding.profileIcon.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

    private fun setupNavigationDrawer() {
        drawerLayout = binding.drawerLayout
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, binding.toolbar,
            com.cybershield.R.string.navigation_drawer_open,
            com.cybershield.R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener(this)
    }

    private fun setupOffersCarousel() {
        val offers = DummyData.getBankOffers()
        val adapter = OfferAdapter(offers)
        binding.offersRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.offersRecyclerView.adapter = adapter
    }

    private fun setupNavigationCards() {
        binding.profileCard.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        binding.loansCard.setOnClickListener {
            startActivity(Intent(this, LoansActivity::class.java))
        }
        binding.schemesCard.setOnClickListener {
            startActivity(Intent(this, GovernmentSchemesActivity::class.java))
        }
        binding.investmentsCard.setOnClickListener {
            startActivity(Intent(this, InvestmentsActivity::class.java))
        }
        binding.transactionsCard.setOnClickListener {
            startActivity(Intent(this, TransactionHistoryActivity::class.java))
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            com.cybershield.R.id.nav_profile -> {
                startActivity(Intent(this, ProfileActivity::class.java))
            }
            com.cybershield.R.id.nav_secure_score -> {
                Toast.makeText(this, "Secure Score: 78/100\nKeep your PIN secure and enable 2FA to improve.", Toast.LENGTH_LONG).show()
            }
            com.cybershield.R.id.nav_pin_creation -> {
                startActivity(Intent(this, PinCreationActivity::class.java))
            }
            com.cybershield.R.id.nav_about -> {
                startActivity(Intent(this, AboutUsActivity::class.java))
            }
            com.cybershield.R.id.nav_loans -> {
                startActivity(Intent(this, LoansActivity::class.java))
            }
            com.cybershield.R.id.nav_government -> {
                startActivity(Intent(this, GovernmentSchemesActivity::class.java))
            }
            com.cybershield.R.id.nav_investments -> {
                startActivity(Intent(this, InvestmentsActivity::class.java))
            }
            com.cybershield.R.id.nav_news -> {
                startActivity(Intent(this, NewsActivity::class.java))
            }
            com.cybershield.R.id.nav_transaction_process -> {
                startActivity(Intent(this, TransactionProcessActivity::class.java))
            }
            com.cybershield.R.id.nav_transaction_history -> {
                startActivity(Intent(this, TransactionHistoryActivity::class.java))
            }
            com.cybershield.R.id.nav_blockchain -> {
                startActivity(Intent(this, com.cybershield.ui.blockchain.BlockchainActivity::class.java))
            }
            com.cybershield.R.id.nav_home -> {
                // Already on home
            }
            com.cybershield.R.id.nav_logout -> {
                com.cybershield.util.PinManager.clearPin(this)
                startActivity(Intent(this, LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                })
                finish()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
