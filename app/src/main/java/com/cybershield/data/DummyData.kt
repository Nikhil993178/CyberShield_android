package com.cybershield.data

import com.cybershield.data.model.*
import java.util.*

object DummyData {
    
    fun getDummyTransactions(): List<Transaction> {
        val calendar = Calendar.getInstance()
        return listOf(
            Transaction("1", calendar.apply { add(Calendar.DAY_OF_MONTH, -1) }.time, "UPI Payment to Merchant", TransactionType.DEBIT, 500.0, 49500.0),
            Transaction("2", calendar.apply { add(Calendar.DAY_OF_MONTH, -2) }.time, "Salary Credit", TransactionType.CREDIT, 50000.0, 50000.0),
            Transaction("3", calendar.apply { add(Calendar.DAY_OF_MONTH, -3) }.time, "ATM Withdrawal", TransactionType.DEBIT, 2000.0, 0.0),
            Transaction("4", calendar.apply { add(Calendar.DAY_OF_MONTH, -5) }.time, "Net Banking Transfer", TransactionType.DEBIT, 1000.0, 0.0),
            Transaction("5", calendar.apply { add(Calendar.DAY_OF_MONTH, -7) }.time, "Interest Credit", TransactionType.CREDIT, 150.0, 0.0)
        )
    }

    fun getDummyLoans(): List<Loan> {
        return listOf(
            Loan("1", "Home Loan", "Buy your dream home with flexible repayment options", "8.5% - 9.5% p.a."),
            Loan("2", "Education Loan", "Invest in your future with education financing", "7.5% - 8.5% p.a."),
            Loan("3", "Personal Loan", "Quick funds for your personal needs", "10.5% - 12% p.a."),
            Loan("4", "Vehicle Loan", "Drive your dream vehicle home", "8.0% - 9.0% p.a."),
            Loan("5", "Agriculture Loan", "Support for farmers and agriculture", "4.0% - 7.0% p.a."),
            Loan("6", "MSME / Business Loan", "Grow your business with our financing", "9.0% - 11% p.a.")
        )
    }

    fun getDummySchemes(): List<GovernmentScheme> {
        return listOf(
            GovernmentScheme("1", "Jan Dhan Yojana", "Financial inclusion scheme providing bank accounts, insurance, and pension"),
            GovernmentScheme("2", "Sukanya Samriddhi Yojana", "Savings scheme for girl child with attractive interest rates"),
            GovernmentScheme("3", "PM Kisan", "Direct income support scheme for farmers"),
            GovernmentScheme("4", "Atal Pension Yojana", "Pension scheme for unorganized sector workers"),
            GovernmentScheme("5", "PM Mudra Yojana", "Micro finance scheme for small businesses")
        )
    }

    fun getDummyNews(): List<NewsItem> {
        val calendar = Calendar.getInstance()
        return listOf(
            NewsItem("1", "Global Banking Regulations Update", "International Banking News", "New regulations announced for cross-border transactions", calendar.apply { add(Calendar.DAY_OF_MONTH, -1) }.time, NewsCategory.INTERNATIONAL),
            NewsItem("2", "Digital Currency Adoption", "International Banking News", "Major banks adopt digital currency solutions", calendar.apply { add(Calendar.DAY_OF_MONTH, -2) }.time, NewsCategory.INTERNATIONAL),
            NewsItem("3", "RBI Announces New Policy", "National Banking News", "Reserve Bank updates monetary policy guidelines", calendar.apply { add(Calendar.DAY_OF_MONTH, -1) }.time, NewsCategory.NATIONAL),
            NewsItem("4", "UPI Transactions Cross Milestone", "National Banking News", "UPI transactions reach new record high", calendar.apply { add(Calendar.DAY_OF_MONTH, -3) }.time, NewsCategory.NATIONAL),
            NewsItem("5", "State Bank Branch Opens", "Local News", "New branch inaugurated in city center", calendar.apply { add(Calendar.DAY_OF_MONTH, -5) }.time, NewsCategory.LOCAL),
            NewsItem("6", "Local Banking Awareness Drive", "Local News", "Bank organizes financial literacy program", calendar.apply { add(Calendar.DAY_OF_MONTH, -7) }.time, NewsCategory.LOCAL)
        )
    }
}

data class GovernmentScheme(
    val id: String,
    val name: String,
    val description: String
)

fun getBankOffers(): List<com.cybershield.data.model.BankOffer> {
    return listOf(
        com.cybershield.data.model.BankOffer("1", "Savings Account", "Interest 6.5% annually"),
        com.cybershield.data.model.BankOffer("2", "Cashback Offer", "5% cashback on transactions above ₹1000"),
        com.cybershield.data.model.BankOffer("3", "Education Loan", "8.9% interest rate"),
        com.cybershield.data.model.BankOffer("4", "Fixed Deposit", "7.0% interest for 1 year"),
        com.cybershield.data.model.BankOffer("5", "Credit Card", "Zero annual fee for first year")
    )
}

