package com.cybershield.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    private val _phone = MutableLiveData<String>()
    val phone: LiveData<String> = _phone

    private val _phoneError = MutableLiveData<String?>()
    val phoneError: LiveData<String?> = _phoneError

    private val _canContinue = MutableLiveData(false)
    val canContinue: LiveData<Boolean> = _canContinue

    private val _recaptchaVerified = MutableLiveData(false)
    val recaptchaVerified: LiveData<Boolean> = _recaptchaVerified

    fun onPhoneChanged(value: String) {
        _phone.value = value
        validatePhone(value.trim())
        updateContinue()
    }

    private fun validatePhone(phone: String) {
        when {
            phone.isEmpty() -> {
                _phoneError.value = null // No error when empty
            }
            phone.length < 10 -> {
                _phoneError.value = "Phone number must be 10 digits"
            }
            phone.length > 10 -> {
                _phoneError.value = "Phone number cannot exceed 10 digits"
            }
            else -> {
                _phoneError.value = null // Valid
            }
        }
    }

    fun markRecaptchaVerified(verified: Boolean) {
        _recaptchaVerified.value = verified
        updateContinue()
    }

    private fun updateContinue() {
        val phone = _phone.value?.trim() ?: ""
        val phoneOk = phone.length == 10 && _phoneError.value == null
        _canContinue.value = phoneOk && (_recaptchaVerified.value == true)
    }
}

