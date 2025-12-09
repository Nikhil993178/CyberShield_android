package com.cybershield.data.model

data class Loan(
    val id: String,
    val name: String,
    val description: String,
    val interestRate: String,
    val iconRes: Int = 0
)

