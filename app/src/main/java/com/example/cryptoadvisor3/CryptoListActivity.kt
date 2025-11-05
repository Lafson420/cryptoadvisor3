package com.example.cryptoadvisor3

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CryptoListActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var cryptoList: MutableList<CoinListItem>
    private lateinit var adapter: CryptoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crypto_list)

        listView = findViewById(R.id.cryptoListView)
        cryptoList = mutableListOf()
        adapter = CryptoAdapter(this, cryptoList)
        listView.adapter = adapter

        loadTopCoins()

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedCoin = cryptoList[position]
            val intent = Intent(this, ChartActivity::class.java)
            intent.putExtra("coin_id", selectedCoin.id)
            startActivity(intent)
        }
    }

    private fun loadTopCoins() {
        val apiService = ApiClient.apiService
        val call = apiService.getTopCoins("usd")

        call.enqueue(object : Callback<List<CoinListItem>> {
            override fun onResponse(
                call: Call<List<CoinListItem>>,
                response: Response<List<CoinListItem>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    cryptoList.clear()
                    cryptoList.addAll(response.body()!!)
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@CryptoListActivity, "Błąd pobierania danych", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<CoinListItem>>, t: Throwable) {
                Toast.makeText(this@CryptoListActivity, "Błąd połączenia z API", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
