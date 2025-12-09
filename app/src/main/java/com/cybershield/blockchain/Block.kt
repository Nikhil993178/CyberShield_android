package com.cybershield.blockchain

import java.security.MessageDigest
import java.util.Date

data class Block(
    val index: Int,
    val timestamp: Long,
    val data: TransactionData,
    val previousHash: String
) {
    val hash: String = calculateHash()

    private fun calculateHash(): String {
        val input = "$index$timestamp${data.sender}${data.receiver}${data.amount}$previousHash"
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(input.toByteArray())
        return hash.joinToString("") { "%02x".format(it) }
    }
}

data class TransactionData(
    val sender: String,
    val receiver: String,
    val amount: Double,
    val description: String = ""
)


