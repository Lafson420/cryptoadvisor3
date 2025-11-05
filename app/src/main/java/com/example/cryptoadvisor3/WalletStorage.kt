package com.example.cryptoadvisor3

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object WalletStorage {
    private const val PREFS_NAME = "wallet_prefs"
    private const val KEY_WALLET = "wallet_data"

    private val gson = Gson()

    fun saveWallet(context: Context, wallet: List<WalletItem>) {
        val json = gson.toJson(wallet)
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_WALLET, json).apply()
    }

    fun loadWallet(context: Context): MutableList<WalletItem> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY_WALLET, null)
        return if (json != null) {
            val type = object : TypeToken<MutableList<WalletItem>>() {}.type
            gson.fromJson(json, type)
        } else {
            mutableListOf()
        }
    }
}
