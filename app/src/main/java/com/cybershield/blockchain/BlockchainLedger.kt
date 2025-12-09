package com.cybershield.blockchain

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object BlockchainLedger {
    private const val PREFS_NAME = "blockchain_ledger"
    private const val KEY_CHAIN = "blockchain_chain"
    private val gson = Gson()

    private val chain = mutableListOf<Block>()

    fun initialize(context: Context) {
        loadChain(context)
        if (chain.isEmpty()) {
            // Create genesis block
            val genesisBlock = Block(
                index = 0,
                timestamp = System.currentTimeMillis(),
                data = TransactionData("SYSTEM", "SYSTEM", 0.0, "Genesis Block"),
                previousHash = "0"
            )
            chain.add(genesisBlock)
            saveChain(context)
        }
    }

    fun addTransaction(context: Context, transactionData: TransactionData): Block {
        val previousBlock = chain.last()
        val newBlock = Block(
            index = chain.size,
            timestamp = System.currentTimeMillis(),
            data = transactionData,
            previousHash = previousBlock.hash
        )
        chain.add(newBlock)
        saveChain(context)
        return newBlock
    }

    fun getChain(): List<Block> = chain.toList()

    fun isChainValid(): Boolean {
        for (i in 1 until chain.size) {
            val currentBlock = chain[i]
            val previousBlock = chain[i - 1]

            // Check if current block hash is valid
            val expectedHash = currentBlock.calculateHash()
            if (currentBlock.hash != expectedHash) {
                return false
            }

            // Check if previous hash matches
            if (currentBlock.previousHash != previousBlock.hash) {
                return false
            }
        }
        return true
    }

    private fun saveChain(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val chainJson = gson.toJson(chain)
        prefs.edit().putString(KEY_CHAIN, chainJson).apply()
    }

    private fun loadChain(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val chainJson = prefs.getString(KEY_CHAIN, null)
        if (chainJson != null) {
            val type = object : TypeToken<List<Block>>() {}.type
            val loadedChain: List<Block> = gson.fromJson(chainJson, type)
            chain.clear()
            chain.addAll(loadedChain)
        }
    }

}

