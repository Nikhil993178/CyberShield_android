package com.cybershield.data.model

import java.util.Date

data class Transaction(
    val id: String,
    val date: Date,
    val description: String,
    val type: TransactionType,
    val amount: Double,
    val runningBalance: Double
)

enum class TransactionType {
    CREDIT, DEBIT
}

