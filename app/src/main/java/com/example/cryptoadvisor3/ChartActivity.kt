package com.example.cryptoadvisor3

import android.graphics.Color
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class ChartActivity : AppCompatActivity() {

    private lateinit var lineChart: LineChart
    private lateinit var daysSpinner: Spinner
    private lateinit var currencySpinner: Spinner
    private lateinit var cryptoNameTextView: TextView
    private lateinit var cryptoPriceTextView: TextView
    private lateinit var cryptoIcon: ImageView

    private var cryptoId: String? = null
    private var cryptoName: String? = null
    private var cryptoIconUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)

        lineChart = findViewById(R.id.lineChart)
        daysSpinner = findViewById(R.id.daysSpinner)
        currencySpinner = findViewById(R.id.currencySpinner)
        cryptoNameTextView = findViewById(R.id.cryptoNameTextView)
        cryptoPriceTextView = findViewById(R.id.cryptoPriceTextView)
        cryptoIcon = findViewById(R.id.cryptoIcon)

        cryptoId = intent.getStringExtra("cryptoId")
        cryptoName = intent.getStringExtra("cryptoName")
        cryptoIconUrl = intent.getStringExtra("cryptoImage")

        cryptoNameTextView.text = cryptoName ?: "Crypto"
        Glide.with(this)
            .load(cryptoIconUrl)
            .placeholder(R.drawable.ic_placeholder_crypto)
            .into(cryptoIcon)

        setupSpinners()
        setupChart()
        fetchChartData(cryptoId ?: "bitcoin", "usd", "7")
    }

    private fun setupSpinners() {
        val adapterDays = ArrayAdapter.createFromResource(
            this,
            R.array.days_array,
            R.layout.spinner_item
        )
        adapterDays.setDropDownViewResource(R.layout.spinner_item)
        daysSpinner.adapter = adapterDays

        val adapterCurrency = ArrayAdapter.createFromResource(
            this,
            R.array.currency_array,
            R.layout.spinner_item
        )
        adapterCurrency.setDropDownViewResource(R.layout.spinner_item)
        currencySpinner.adapter = adapterCurrency

        daysSpinner.setSelection(1)
        currencySpinner.setSelection(0)

        daysSpinner.setOnItemSelectedListener(SimpleItemSelectedListener {
            updateChart()
        })

        currencySpinner.setOnItemSelectedListener(SimpleItemSelectedListener {
            updateChart()
        })
    }

    private fun updateChart() {
        val selectedDays = daysSpinner.selectedItem.toString()
        val selectedCurrency = currencySpinner.selectedItem.toString()
        fetchChartData(cryptoId ?: "bitcoin", selectedCurrency, selectedDays)
    }

    private fun setupChart() {
        lineChart.setBackgroundColor(Color.BLACK)
        lineChart.description.isEnabled = false
        lineChart.legend.textColor = Color.WHITE

        val xAxis = lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.textColor = Color.WHITE
        xAxis.setDrawGridLines(true)
        xAxis.granularity = 1f
        xAxis.valueFormatter = object : ValueFormatter() {
            private val dateFormat = SimpleDateFormat("dd/MM", Locale.getDefault())
            override fun getFormattedValue(value: Float): String {
                return dateFormat.format(Date(value.toLong()))
            }
        }

        lineChart.axisLeft.textColor = Color.WHITE
        lineChart.axisLeft.setDrawGridLines(true)
        lineChart.axisRight.isEnabled = false
    }

    private fun fetchChartData(cryptoId: String, currency: String, days: String) {
        val api = RetrofitInstance.api
        api.getCoinMarketChart(cryptoId, currency, days)
            .enqueue(object : Callback<MarketChartResponse> {
                override fun onResponse(
                    call: Call<MarketChartResponse>,
                    response: Response<MarketChartResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { data ->
                            displayChart(data)
                            updatePriceLabel(data)
                        }
                    }
                }

                override fun onFailure(call: Call<MarketChartResponse>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }

    private fun updatePriceLabel(data: MarketChartResponse) {
        if (data.prices.isNotEmpty()) {
            val lastPrice = data.prices.last()[1]
            val formatter = DecimalFormat("#,###.00")
            cryptoPriceTextView.text = "${formatter.format(lastPrice)} USD"
        }
    }

    private fun displayChart(data: MarketChartResponse) {
        val entries = mutableListOf<Entry>()
        for (pricePoint in data.prices) {
            val time = pricePoint[0].toFloat()
            val price = pricePoint[1].toFloat()
            entries.add(Entry(time, price))
        }

        val dataSet = LineDataSet(entries, "${cryptoName ?: "Crypto"} (${currencySpinner.selectedItem})")
        dataSet.color = Color.MAGENTA
        dataSet.setDrawCircles(false)
        dataSet.lineWidth = 2f
        dataSet.setDrawFilled(true)
        dataSet.fillColor = Color.MAGENTA
        dataSet.fillAlpha = 80
        dataSet.valueTextColor = Color.WHITE
        dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER

        val lineData = LineData(dataSet)
        lineData.setValueTextColor(Color.WHITE)
        lineData.setValueTextSize(9f)

        lineChart.data = lineData
        lineChart.invalidate()
    }
}
