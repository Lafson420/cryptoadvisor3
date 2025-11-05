package com.example.cryptoadvisor3

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class WalletActivity : AppCompatActivity() {

    private lateinit var totalTextView: TextView
    private lateinit var addCryptoButton: Button
    private lateinit var listView: ListView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var currencySpinner: Spinner

    private val walletList = mutableListOf<WalletItem>()
    private lateinit var adapter: WalletAdapter
    private var selectedCurrency = "usd"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)

        try {
            totalTextView = findViewById(R.id.totalTextView)
            addCryptoButton = findViewById(R.id.addCryptoButton)
            listView = findViewById(R.id.listView)
            swipeRefresh = findViewById(R.id.swipeRefresh)
            currencySpinner = findViewById(R.id.currencySpinner)

            adapter = WalletAdapter(this, walletList)
            listView.adapter = adapter

            setupCurrencySpinner()

            addCryptoButton.setOnClickListener {
                val intent = Intent(this, AddCryptoActivity::class.java)
                startActivityForResult(intent, 1)
            }

            swipeRefresh.setOnRefreshListener {
                updateTotal()
                swipeRefresh.isRefreshing = false
            }

            updateTotal()

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Błąd inicjalizacji: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun setupCurrencySpinner() {
        val currencies = listOf("usd", "eur", "pln")
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        currencySpinner.adapter = spinnerAdapter
        currencySpinner.setSelection(0)
        currencySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: android.view.View?,
                position: Int,
                id: Long
            ) {
                selectedCurrency = currencies[position]
                updateTotal()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            val id = data.getStringExtra("id") ?: ""
            val name = data.getStringExtra("name") ?: ""
            val symbol = data.getStringExtra("symbol") ?: ""
            val amount = data.getDoubleExtra("amount", 0.0)
            val valueUsd = data.getDoubleExtra("valueUsd", 0.0)

            if (name.isNotEmpty() && symbol.isNotEmpty()) {
                val newItem = WalletItem(
                    id = id,
                    name = name,
                    symbol = symbol,
                    amount = amount,
                    valueUsd = valueUsd
                )
                walletList.add(newItem)
                adapter.notifyDataSetChanged()
                updateTotal()
                Toast.makeText(this, "Dodano ${newItem.name}", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Błąd: brak danych kryptowaluty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateTotal() {
        val totalUsd = walletList.sumOf { it.amount * it.valueUsd }
        totalTextView.text = String.format("%.2f USD", totalUsd)
    }
}
