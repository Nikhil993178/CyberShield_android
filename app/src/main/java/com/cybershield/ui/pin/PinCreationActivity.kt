package com.cybershield.ui.pin

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.cybershield.databinding.ActivityPinCreationBinding
import com.cybershield.util.PinManager
import kotlin.random.Random

class PinCreationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPinCreationBinding
    private var currentStep = Step.LINK_CONTACT
    private var verifiedContact: String = ""

    enum class Step {
        LINK_CONTACT, VERIFY_OTP, CREATE_PIN
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPinCreationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        showLinkContactStep()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "PIN Creation"
    }

    private fun showLinkContactStep() {
        currentStep = Step.LINK_CONTACT
        binding.stepTitle.text = "Step 1: Link Contact"
        binding.stepDescription.text = "Add your phone number or email to secure your account"
        binding.inputField.hint = "Phone number or Email"
        binding.inputField.inputType = InputType.TYPE_CLASS_TEXT
        binding.inputField.text?.clear()
        binding.actionButton.text = "Continue"
        binding.actionButton.setOnClickListener {
            val contact = binding.inputField.text.toString().trim()
            if (contact.isEmpty()) {
                Toast.makeText(this, "Please enter phone number or email", Toast.LENGTH_SHORT).show()
            } else {
                verifiedContact = contact
                showOtpVerificationStep()
            }
        }
    }

    private fun showOtpVerificationStep() {
        currentStep = Step.VERIFY_OTP
        binding.stepTitle.text = "Step 2: Verify OTP"
        binding.stepDescription.text = "OTP sent to $verifiedContact"
        
        // Generate dummy OTP
        val dummyOtp = "123456" // For demo, always use 123456
        Toast.makeText(this, "OTP Generated: $dummyOtp (Demo)", Toast.LENGTH_LONG).show()
        
        binding.inputField.hint = "Enter 6-digit OTP"
        binding.inputField.inputType = InputType.TYPE_CLASS_NUMBER
        binding.inputField.text?.clear()
        binding.actionButton.text = "Verify OTP"
        binding.actionButton.setOnClickListener {
            val enteredOtp = binding.inputField.text.toString().trim()
            if (enteredOtp == dummyOtp) {
                showCreatePinStep()
            } else {
                Toast.makeText(this, "Invalid OTP. Please try again.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showCreatePinStep() {
        currentStep = Step.CREATE_PIN
        binding.stepTitle.text = "Step 3: Create PIN"
        binding.stepDescription.text = "Enter a 4-6 digit PIN to secure your account"
        binding.inputField.hint = "Enter PIN (4-6 digits)"
        binding.inputField.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD
        binding.inputField.text?.clear()
        binding.actionButton.text = "Create PIN"
        
        // Show PIN confirmation dialog when button is clicked
        binding.actionButton.setOnClickListener {
            val pin = binding.inputField.text.toString().trim()
            if (pin.length < 4 || pin.length > 6) {
                Toast.makeText(this, "PIN must be 4-6 digits", Toast.LENGTH_SHORT).show()
            } else {
                showPinConfirmationDialog(pin)
            }
        }
    }

    private fun showPinConfirmationDialog(firstPin: String) {
        val dialogView = LayoutInflater.from(this).inflate(com.cybershield.R.layout.dialog_pin_confirmation, null)
        val confirmPinInput = dialogView.findViewById<EditText>(com.cybershield.R.id.confirmPinInput)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Re-enter PIN")
            .setView(dialogView)
            .setPositiveButton("Confirm") { _, _ ->
                val confirmPin = confirmPinInput.text.toString().trim()
                if (firstPin == confirmPin) {
                    if (PinManager.setPin(this, firstPin)) {
                        Toast.makeText(this, "PIN created successfully. Use it to view balance.", Toast.LENGTH_LONG).show()
                        finish()
                    } else {
                        Toast.makeText(this, "Failed to create PIN", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "PINs do not match. Please try again.", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

