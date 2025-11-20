package com.example.cryptoadvisor3

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CryptoListActivity : AppCompatActivity() {

    private lateinit var cryptoListView: ListView
    private lateinit var adapter: CryptoListAdapter
    private val cryptoList = mutableListOf<CryptoCurrency>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crypto_list)

        cryptoListView = findViewById(R.id.cryptoListView)
        adapter = CryptoListAdapter(this, cryptoList)
        cryptoListView.adapter = adapter

        cryptoListView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val selectedCrypto = cryptoList[position]
            val intent = Intent(this, ChartActivity::class.java)
            intent.putExtra("cryptoId", selectedCrypto.id)
            intent.putExtra("cryptoName", selectedCrypto.name)
            intent.putExtra("cryptoImage", selectedCrypto.image)
            startActivity(intent)
        }

        fetchCryptoData()
    }

    private fun fetchCryptoData() {
        val api = RetrofitInstance.api
        api.getTopCoins("usd").enqueue(object : Callback<List<CryptoCurrency>> {
            override fun onResponse(call: Call<List<CryptoCurrency>>, response: Response<List<CryptoCurrency>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        cryptoList.clear()
                        cryptoList.addAll(it)
                        adapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<List<CryptoCurrency>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}
