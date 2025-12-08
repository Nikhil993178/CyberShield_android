package com.cybershield.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cybershield.data.AuthRepository
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class OtpViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {

    private val _verificationId = MutableLiveData<String>()
    val verificationId: LiveData<String> = _verificationId

    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    fun cacheVerificationId(id: String) {
        _verificationId.value = id
    }

    fun verifyOtp(code: String, onComplete: (Boolean, String?) -> Unit) {
        val id = _verificationId.value
        if (id.isNullOrBlank()) {
            onComplete(false, "Verification ID missing")
            return
        }
        val credential = PhoneAuthProvider.getCredential(id, code)
        repository.signInWithCredential(credential) { success, error ->
            _status.postValue(if (success) "verified" else "error")
            onComplete(success, error)
        }
    }

    fun resendOtp(
        phoneNumber: String,
        activity: android.app.Activity,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {
        repository.sendOtp(phoneNumber, activity, callbacks)
    }
}

