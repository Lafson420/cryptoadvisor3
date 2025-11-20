package com.example.cryptoadvisor3

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call

// API serwisu CoinGecko
interface CryptoApiService {

    // Lista topowych kryptowalut (nazwa, cena, ikona)
    @GET("coins/markets")
    fun getTopCoins(
        @Query("vs_currency") vsCurrency: String
    ): Call<List<CryptoCurrency>>

    // Dane do wykresu konkretnej kryptowaluty
    @GET("coins/{id}/market_chart")
    fun getCoinMarketChart(
        @retrofit2.http.Path("id") id: String,
        @Query("vs_currency") vsCurrency: String,
        @Query("days") days: String
    ): Call<MarketChartResponse>
}

// Singleton Retrofit
object RetrofitInstance {
    private const val BASE_URL = "https://api.coingecko.com/api/v3/"

    val api: CryptoApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptoApiService::class.java)
    }
}
