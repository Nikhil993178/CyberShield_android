package com.cybershield.ui.profile

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.InputType
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.cybershield.databinding.ActivityProfileBinding
import com.cybershield.ui.auth.LoginActivity
import com.cybershield.util.PinManager
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import java.util.Hashtable

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private var isBalanceVisible = false
    private val dummyBalance = "₹ 50,000.00"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupProfile()
        setupBalanceRow()
        setupLogout()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Profile"
    }

    private fun setupProfile() {
        binding.userName.text = "NIKHIL KUMAR"
        binding.balanceValue.text = "₹ ••••••"
        binding.bankName.text = "Cyber Shield Bank"
        binding.accountNumber.text = "XXXX XXXX 2345"
        binding.ifscCode.text = "CSHB0000123"
        setupQRCode()
    }

    private fun setupQRCode() {
        binding.qrCodeButton.setOnClickListener {
            showQRCodeDialog()
        }
        binding.shareQrButton.setOnClickListener {
            shareQRCode()
        }
    }

    private fun showQRCodeDialog() {
        val qrData = "UPI:${binding.accountNumber.text}@cybershield|NIKHIL KUMAR"
        val bitmap = generateQRCode(qrData, 400, 400)
        
        if (bitmap != null) {
            val dialogView = LayoutInflater.from(this).inflate(com.cybershield.R.layout.dialog_qr_code, null)
            val qrImageView = dialogView.findViewById<android.widget.ImageView>(com.cybershield.R.id.qrCodeImage)
            qrImageView.setImageBitmap(bitmap)
            
            AlertDialog.Builder(this)
                .setTitle("QR Code")
                .setView(dialogView)
                .setPositiveButton("Close", null)
                .show()
        } else {
            Toast.makeText(this, "Failed to generate QR code", Toast.LENGTH_SHORT).show()
        }
    }

    private fun generateQRCode(text: String, width: Int, height: Int): Bitmap? {
        return try {
            val hints = Hashtable<EncodeHintType, Any>()
            hints[EncodeHintType.CHARACTER_SET] = "UTF-8"
            val writer = QRCodeWriter()
            val bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, width, height, hints)
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
                }
            }
            bitmap
        } catch (e: WriterException) {
            null
        }
    }

    private fun shareQRCode() {
        val qrData = "UPI:${binding.accountNumber.text}@cybershield|NIKHIL KUMAR"
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "My UPI QR Code: $qrData")
        }
        startActivity(Intent.createChooser(shareIntent, "Share QR Code"))
    }

    private fun setupBalanceRow() {
        binding.balanceRow.setOnClickListener {
            if (!PinManager.isPinSet(this)) {
                Toast.makeText(this, "Please create a PIN first.", Toast.LENGTH_LONG).show()
            } else {
                showPinVerificationDialog()
            }
        }
    }

    private fun showPinVerificationDialog() {
        val dialogView = LayoutInflater.from(this).inflate(com.cybershield.R.layout.dialog_pin_verification, null)
        val pinInput = dialogView.findViewById<EditText>(com.cybershield.R.id.pinInput)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Enter PIN")
            .setView(dialogView)
            .setPositiveButton("Verify") { _, _ ->
                val pin = pinInput.text.toString()
                if (PinManager.verifyPin(this, pin)) {
                    revealBalance()
                } else {
                    Toast.makeText(this, "Invalid PIN", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }

    private fun revealBalance() {
        isBalanceVisible = true
        binding.balanceValue.text = dummyBalance
        Toast.makeText(this, "Balance revealed", Toast.LENGTH_SHORT).show()

        // Hide balance after 5 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            if (isBalanceVisible) {
                binding.balanceValue.text = "₹ ••••••"
                isBalanceVisible = false
            }
        }, 5000)
    }

    private fun setupLogout() {
        binding.logoutButton.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes") { _, _ ->
                    // Clear session
                    PinManager.clearPin(this) // Optional: clear PIN on logout
                    startActivity(Intent(this, LoginActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    })
                    finish()
                }
                .setNegativeButton("No", null)
                .show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

