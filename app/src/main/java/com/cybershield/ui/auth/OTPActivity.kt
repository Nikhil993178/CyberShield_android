package com.cybershield.ui.auth

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cybershield.databinding.ActivityOtpBinding
import com.cybershield.ui.dashboard.DashboardActivity
import com.google.android.material.snackbar.Snackbar
import kotlin.random.Random

class OTPActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpBinding
    private var correctOtp: String = ""
    private var attempts = 0
    private var frozen = false
    private var countDownTimer: CountDownTimer? = null
    private val RESEND_DELAY_SECONDS = 15L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val phone = intent.getStringExtra(EXTRA_PHONE) ?: ""
        correctOtp = intent.getStringExtra(EXTRA_OTP) ?: ""
        
        // Mask phone number (show only last 3 digits)
        val maskedPhone = maskPhoneNumber(phone)
        binding.phoneText.text = maskedPhone

        if (correctOtp.isEmpty()) {
            Toast.makeText(this, "OTP not found. Please generate again.", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        // Start countdown for resend button
        startResendCountdown()

        binding.verifyButton.setOnClickListener {
            if (frozen) {
                Toast.makeText(this, getString(com.cybershield.R.string.otp_freeze), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            verifyOtp()
        }

        binding.resendButton.setOnClickListener {
            if (frozen) {
                Toast.makeText(this, getString(com.cybershield.R.string.otp_freeze), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            resendOtp(phone)
            startResendCountdown() // Restart countdown after resend
        }
    }

    private fun startResendCountdown() {
        binding.resendButton.visibility = android.view.View.GONE
        binding.resendButton.isEnabled = false
        
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(RESEND_DELAY_SECONDS * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                binding.resendButton.text = getString(com.cybershield.R.string.otp_resend_countdown, secondsRemaining)
                binding.resendButton.visibility = android.view.View.VISIBLE
            }

            override fun onFinish() {
                binding.resendButton.text = getString(com.cybershield.R.string.otp_resend)
                binding.resendButton.isEnabled = true
                binding.resendButton.visibility = android.view.View.VISIBLE
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }

    private fun verifyOtp() {
        val enteredOtp = binding.otpEdit.text.toString().trim()
        
        if (enteredOtp.isEmpty()) {
            Toast.makeText(this, "Please enter OTP", Toast.LENGTH_SHORT).show()
            return
        }

        if (enteredOtp == correctOtp) {
            // Success
            Toast.makeText(this, getString(com.cybershield.R.string.otp_success), Toast.LENGTH_SHORT).show()
            handleSuccessfulLogin()
        } else {
            // Wrong OTP
            handleFailedAttempt()
        }
    }

    private fun handleFailedAttempt() {
        attempts += 1
        
        if (attempts == 1) {
            // First wrong attempt - show alert message
            Toast.makeText(this, getString(com.cybershield.R.string.otp_first_fail), Toast.LENGTH_LONG).show()
        }
        
        // Show invalid OTP message in big font at bottom
        showError(getString(com.cybershield.R.string.otp_error_invalid))
        
        // Clear OTP field
        binding.otpEdit.text?.clear()
        
        // Freeze after 3 attempts
        if (attempts >= 3) {
            freezeAccount()
        }
    }

    private fun showError(message: String) {
        binding.errorText.text = message
        binding.errorText.visibility = android.view.View.VISIBLE
        
        // Hide error after 3 seconds
        binding.errorText.postDelayed({
            binding.errorText.visibility = android.view.View.GONE
        }, 3000)
    }

    private fun freezeAccount() {
        frozen = true
        binding.verifyButton.isEnabled = false
        binding.resendButton.isEnabled = false
        binding.otpEdit.isEnabled = false
        
        Toast.makeText(this, getString(com.cybershield.R.string.otp_freeze), Toast.LENGTH_LONG).show()
        Snackbar.make(binding.root, getString(com.cybershield.R.string.otp_unfreeze_info), Snackbar.LENGTH_LONG).show()
    }

    private fun resendOtp(phone: String) {
        // Generate new random 6-digit OTP
        correctOtp = Random.nextInt(100000, 999999).toString()
        
        val message = getString(com.cybershield.R.string.otp_generated, correctOtp)
        Toast.makeText(this, "OTP resent to $phone: $correctOtp", Toast.LENGTH_LONG).show()
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
        
        // Clear previous OTP input
        binding.otpEdit.text?.clear()
    }

    private fun handleSuccessfulLogin() {
        // Check for new device login
        if (isNewDevice()) {
            Toast.makeText(this, getString(com.cybershield.R.string.otp_new_device), Toast.LENGTH_LONG).show()
        }
        goToDashboard()
    }

    private fun isNewDevice(): Boolean {
        val prefs = getSharedPreferences("cybershield_prefs", MODE_PRIVATE)
        val deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID) ?: "unknown"
        val storedId = prefs.getString("device_id", null)
        val isNew = storedId == null || storedId != deviceId
        if (isNew) {
            prefs.edit().putString("device_id", deviceId).apply()
        }
        return isNew
    }

    private fun goToDashboard() {
        startActivity(Intent(this, DashboardActivity::class.java))
        finish()
    }

    private fun maskPhoneNumber(phone: String): String {
        if (phone.length <= 3) return phone
        val lastThree = phone.takeLast(3)
        val masked = "*******$lastThree"
        return masked
    }

    companion object {
        const val EXTRA_PHONE = "extra_phone"
        const val EXTRA_OTP = "extra_otp"
    }
}

