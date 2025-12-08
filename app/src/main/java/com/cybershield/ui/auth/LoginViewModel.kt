package com.cybershield.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    private val _phone = MutableLiveData<String>()
    val phone: LiveData<String> = _phone

    private val _canContinue = MutableLiveData(false)
    val canContinue: LiveData<Boolean> = _canContinue

    private val _recaptchaVerified = MutableLiveData(false)
    val recaptchaVerified: LiveData<Boolean> = _recaptchaVerified

    fun onPhoneChanged(value: String) {
        _phone.value = value
        updateContinue()
    }

    fun markRecaptchaVerified(verified: Boolean) {
        _recaptchaVerified.value = verified
        updateContinue()
    }

    private fun updateContinue() {
        val phoneOk = _phone.value?.trim()?.length ?: 0 >= 10
        _canContinue.value = phoneOk && (_recaptchaVerified.value == true)
    }
}

