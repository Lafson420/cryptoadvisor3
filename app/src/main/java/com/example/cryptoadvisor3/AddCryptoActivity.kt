package com.example.cryptoadvisor3

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddCryptoActivity : AppCompatActivity() {

    private lateinit var searchView: SearchView
    private lateinit var listView: ListView
    private lateinit var amountEditText: EditText
    private lateinit var addButton: Button
    private lateinit var backButton: ImageView

    private val searchResults = mutableListOf<CoinListItem>()
    private lateinit var adapter: CryptoAdapter
    private var selectedCoin: CoinListItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_crypto)

        backButton = findViewById(R.id.backButton)
        searchView = findViewById(R.id.searchView)
        listView = findViewById(R.id.cryptoListView)
        amountEditText = findViewById(R.id.amountEditText)
        addButton = findViewById(R.id.addButton)

        adapter = CryptoAdapter(this, searchResults)
        listView.adapter = adapter

        backButton.setOnClickListener { finish() }

        setupSearch()
        setupAddButton()
    }

    private fun setupSearch() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) searchCoins(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) searchCoins(newText)
                return true
            }
        })

        listView.setOnItemClickListener { _, _, position, _ ->
            selectedCoin = searchResults[position]
            Toast.makeText(
                this,
                "Wybrano ${selectedCoin!!.name}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun searchCoins(query: String) {
        val apiService = ApiClient.apiService
        val call = apiService.searchCoins(query)

        call.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val coinIds = response.body()!!.coins.map { it.id }.joinToString(",")
                    if (coinIds.isNotEmpty()) fetchCoinPrices(coinIds)
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Toast.makeText(this@AddCryptoActivity, "Błąd wyszukiwania", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchCoinPrices(ids: String) {
        val apiService = ApiClient.apiService
        val call = apiService.getTopCoins("usd", perPage = 250)

        call.enqueue(object : Callback<List<CoinListItem>> {
            override fun onResponse(call: Call<List<CoinListItem>>, response: Response<List<CoinListItem>>) {
                if (response.isSuccessful && response.body() != null) {
                    val coins = response.body()!!.filter { ids.contains(it.id) }
                    searchResults.clear()
                    searchResults.addAll(coins)
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<List<CoinListItem>>, t: Throwable) {
                Toast.makeText(this@AddCryptoActivity, "Błąd pobierania cen", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupAddButton() {
        addButton.setOnClickListener {
            val amountText = amountEditText.text.toString()
            val amount = amountText.toDoubleOrNull()

            if (selectedCoin == null) {
                Toast.makeText(this, "Wybierz kryptowalutę", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (amount == null || amount <= 0) {
                Toast.makeText(this, "Podaj poprawną ilość", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent()
            intent.putExtra("id", selectedCoin!!.id)
            intent.putExtra("name", selectedCoin!!.name)
            intent.putExtra("symbol", selectedCoin!!.symbol)
            intent.putExtra("amount", amount)
            intent.putExtra("valueUsd", selectedCoin!!.current_price)

            Toast.makeText(this, "Dodano ${selectedCoin!!.name}", Toast.LENGTH_SHORT).show()
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}
