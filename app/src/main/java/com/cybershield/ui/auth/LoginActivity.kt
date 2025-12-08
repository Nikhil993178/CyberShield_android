package com.cybershield.ui.auth

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.InputFilter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.cybershield.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.random.Random

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    private var recaptchaVerifying = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Limit phone input to 10 digits
        binding.phoneEdit.filters = arrayOf(InputFilter.LengthFilter(10))

        binding.phoneEdit.doOnTextChanged { text, _, _, _ ->
            viewModel.onPhoneChanged(text.toString())
        }

        // Observe phone validation errors
        viewModel.phoneError.observe(this) { error ->
            binding.phoneLayout.error = error
            binding.phoneLayout.isErrorEnabled = error != null
        }

        // Observe continue button state
        viewModel.canContinue.observe(this) { enabled ->
            binding.sendOtpButton.isEnabled = enabled
        }

        // Observe reCAPTCHA verification state
        viewModel.recaptchaVerified.observe(this) { verified ->
            if (verified) {
                binding.recaptchaCheckboxBg.setColorFilter(getColor(com.cybershield.R.color.primary))
                binding.recaptchaCheckmark.visibility = android.view.View.VISIBLE
                binding.recaptchaStatus.text = getString(com.cybershield.R.string.recaptcha_verified)
                binding.recaptchaStatus.setTextColor(getColor(com.cybershield.R.color.primary))
            } else {
                binding.recaptchaCheckboxBg.setColorFilter(getColor(com.cybershield.R.color.secondaryText))
                binding.recaptchaCheckmark.visibility = android.view.View.GONE
                binding.recaptchaStatus.text = getString(com.cybershield.R.string.login_human_pending)
                binding.recaptchaStatus.setTextColor(getColor(com.cybershield.R.color.secondaryText))
            }
        }

        // reCAPTCHA container click handler
        binding.recaptchaContainer.setOnClickListener {
            if (!recaptchaVerifying && viewModel.recaptchaVerified.value != true) {
                startRecaptchaVerification()
            }
        }

        // Generate OTP button
        binding.sendOtpButton.setOnClickListener {
            val phone = binding.phoneEdit.text.toString().trim()
            if (phone.length == 10 && viewModel.recaptchaVerified.value == true) {
                generateAndShowOtp(phone)
            } else {
                if (phone.length != 10) {
                    Toast.makeText(this, getString(com.cybershield.R.string.phone_error_short), Toast.LENGTH_SHORT).show()
                }
                if (viewModel.recaptchaVerified.value != true) {
                    Toast.makeText(this, "Please complete reCAPTCHA verification", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun startRecaptchaVerification() {
        recaptchaVerifying = true
        binding.recaptchaStatus.text = "Verifying..."
        
        // Simulate reCAPTCHA verification (in production, use Firebase reCAPTCHA or Play Integrity)
        Handler(Looper.getMainLooper()).postDelayed({
            // Simulate verification success
            viewModel.markRecaptchaVerified(true)
            recaptchaVerifying = false
            Toast.makeText(this, "reCAPTCHA verified successfully", Toast.LENGTH_SHORT).show()
        }, 1500) // 1.5 second delay to simulate verification
    }

    private fun generateAndShowOtp(phone: String) {
        // Generate random 6-digit OTP (100000 to 999999)
        val otp = Random.nextInt(100000, 999999).toString()
        
        // Show OTP in Toast and Snackbar for demo
        val message = getString(com.cybershield.R.string.otp_generated, otp)
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
        
        // Navigate to OTP screen with phone and generated OTP
        val intent = Intent(this, OTPActivity::class.java).apply {
            putExtra(OTPActivity.EXTRA_PHONE, phone)
            putExtra(OTPActivity.EXTRA_OTP, otp)
        }
        startActivity(intent)
    }
}

private fun android.widget.EditText.doOnTextChanged(block: (CharSequence?, Int, Int, Int) -> Unit) {
    addTextChangedListener(object : android.text.TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            block(s, start, before, count)
        }
        override fun afterTextChanged(s: android.text.Editable?) {}
    })
}

