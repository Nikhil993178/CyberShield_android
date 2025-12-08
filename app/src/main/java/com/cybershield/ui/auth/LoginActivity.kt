package com.cybershield.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.cybershield.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.phoneEdit.doOnTextChanged { text, _, _, _ ->
            viewModel.onPhoneChanged(text.toString())
        }

        viewModel.canContinue.observe(this) { enabled ->
            binding.sendOtpButton.isEnabled = enabled
        }

        viewModel.recaptchaVerified.observe(this) { ok ->
            binding.recaptchaStatus.text = if (ok) {
                getString(com.cybershield.R.string.login_human_ok)
            } else {
                getString(com.cybershield.R.string.login_human_pending)
            }
        }

        binding.recaptchaButton.setOnClickListener {
            // Placeholder: in production, trigger SafetyNet reCAPTCHA / Play Integrity
            viewModel.markRecaptchaVerified(true)
        }

        binding.sendOtpButton.setOnClickListener {
            val phone = binding.phoneEdit.text.toString().trim()
            navigateToOtp(phone)
        }
    }

    private fun navigateToOtp(phone: String) {
        val intent = Intent(this, OTPActivity::class.java).apply {
            putExtra(OTPActivity.EXTRA_PHONE, phone)
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

