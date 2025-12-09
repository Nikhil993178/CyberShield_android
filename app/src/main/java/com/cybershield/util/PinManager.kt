package com.cybershield.util

import android.content.Context
import android.content.SharedPreferences
import java.security.MessageDigest

object PinManager {
    private const val PREFS_NAME = "cybershield_pin_prefs"
    private const val KEY_PIN_HASH = "pin_hash"
    private const val KEY_PIN_SET = "pin_set"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun isPinSet(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_PIN_SET, false)
    }

    fun setPin(context: Context, pin: String): Boolean {
        if (pin.length < 4 || pin.length > 6) {
            return false
        }
        val hash = hashPin(pin)
        getPrefs(context).edit()
            .putString(KEY_PIN_HASH, hash)
            .putBoolean(KEY_PIN_SET, true)
            .apply()
        return true
    }

    fun verifyPin(context: Context, pin: String): Boolean {
        if (!isPinSet(context)) return false
        val storedHash = getPrefs(context).getString(KEY_PIN_HASH, null) ?: return false
        val inputHash = hashPin(pin)
        return storedHash == inputHash
    }

    private fun hashPin(pin: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(pin.toByteArray())
        return hash.joinToString("") { "%02x".format(it) }
    }

    fun clearPin(context: Context) {
        getPrefs(context).edit()
            .remove(KEY_PIN_HASH)
            .putBoolean(KEY_PIN_SET, false)
            .apply()
    }
}

