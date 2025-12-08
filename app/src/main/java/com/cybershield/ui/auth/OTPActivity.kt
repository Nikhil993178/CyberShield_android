package com.cybershield.ui.auth

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.cybershield.databinding.ActivityOtpBinding
import com.cybershield.ui.dashboard.DashboardActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class OTPActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpBinding
    private val viewModel: OtpViewModel by viewModels()
    private var attempts = 0
    private var frozen = false
    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            credential.smsCode?.let { binding.otpEdit.setText(it) }
            signIn(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            Toast.makeText(this@OTPActivity, e.localizedMessage, Toast.LENGTH_LONG).show()
        }

        override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
            viewModel.cacheVerificationId(verificationId)
            Toast.makeText(this@OTPActivity, "OTP sent", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val phone = intent.getStringExtra(EXTRA_PHONE) ?: ""
        binding.phoneText.text = phone

        sendOtp(phone)

        binding.verifyButton.setOnClickListener {
            if (frozen) {
                Toast.makeText(this, getString(com.cybershield.R.string.otp_freeze), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val code = binding.otpEdit.text.toString()
            viewModel.verifyOtp(code) { success, error ->
                if (success) {
                    handleSuccessfulLogin()
                } else {
                    handleFailedAttempt(error)
                }
            }
        }

        binding.resendButton.setOnClickListener { sendOtp(phone, force = true) }
    }

    private fun sendOtp(phone: String, force: Boolean = false) {
        if (phone.isBlank()) {
            Toast.makeText(this, "Phone number missing", Toast.LENGTH_SHORT).show()
            return
        }
        viewModel.resendOtp(phone, this, callbacks)
        if (!force) {
            Toast.makeText(this, "Sending OTP to $phone", Toast.LENGTH_SHORT).show()
        }
    }

    private fun signIn(credential: PhoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    handleSuccessfulLogin()
                } else {
                    handleFailedAttempt(task.exception?.localizedMessage)
                }
            }
    }

    private fun handleFailedAttempt(error: String?) {
        attempts += 1
        val first = attempts == 1
        val message = error ?: "Verification failed"
        if (first) {
            Toast.makeText(this, getString(com.cybershield.R.string.otp_first_fail), Toast.LENGTH_LONG).show()
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        if (attempts >= 3) {
            freezeAccount()
        }
    }

    private fun freezeAccount() {
        frozen = true
        binding.verifyButton.isEnabled = false
        binding.resendButton.isEnabled = false
        Toast.makeText(this, getString(com.cybershield.R.string.otp_freeze), Toast.LENGTH_LONG).show()
        Toast.makeText(this, getString(com.cybershield.R.string.otp_unfreeze_info), Toast.LENGTH_LONG).show()
    }

    private fun handleSuccessfulLogin() {
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

    companion object {
        const val EXTRA_PHONE = "extra_phone"
    }
}

