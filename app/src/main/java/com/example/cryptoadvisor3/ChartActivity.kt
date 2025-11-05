package com.example.cryptoadvisor3

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChartActivity : AppCompatActivity() {

    private lateinit var lineChart: LineChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)

        lineChart = findViewById(R.id.lineChart)

        val coinId = intent.getStringExtra("coin_id") ?: "bitcoin"
        loadChartData(coinId)
    }

    private fun loadChartData(coinId: String) {
        val apiService = ApiClient.apiService

        // Pobieranie danych wykresu z CoinGecko
        val call = apiService.getMarketChart(
            id = coinId,
            vsCurrency = "usd",
            days = 7
        )

        call.enqueue(object : Callback<MarketChartResponse> {
            override fun onResponse(
                call: Call<MarketChartResponse>,
                response: Response<MarketChartResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val prices = response.body()!!.prices
                    showChart(prices)
                } else {
                    Toast.makeText(
                        this@ChartActivity,
                        "Błąd wczytywania danych wykresu",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<MarketChartResponse>, t: Throwable) {
                Toast.makeText(
                    this@ChartActivity,
                    "Błąd połączenia z API",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun showChart(prices: List<List<Double>>) {
        val entries = prices.mapIndexed { index, pair ->
            Entry(index.toFloat(), pair[1].toFloat())
        }

        val dataSet = LineDataSet(entries, "Cena USD")
        dataSet.color = Color.MAGENTA
        dataSet.valueTextColor = Color.WHITE
        dataSet.lineWidth = 2f
        dataSet.setDrawCircles(false)
        dataSet.setDrawValues(false)

        val lineData = LineData(dataSet)
        lineChart.data = lineData
        lineChart.setBackgroundColor(Color.BLACK)

        val description = Description()
        description.text = "Ostatnie 7 dni"
        description.textColor = Color.WHITE
        lineChart.description = description

        lineChart.invalidate()
    }
}
