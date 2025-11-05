package com.example.cryptoadvisor3

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // 📊 Pobiera listę topowych kryptowalut
    @GET("coins/markets")
    fun getTopCoins(
        @Query("vs_currency") currency: String,
        @Query("order") order: String = "market_cap_desc",
        @Query("per_page") perPage: Int = 50,
        @Query("page") page: Int = 1,
        @Query("sparkline") sparkline: Boolean = false
    ): Call<List<CoinListItem>>

    // 🔍 Szukanie kryptowalut po nazwie (np. "pepe")
    @GET("search")
    fun searchCoins(
        @Query("query") query: String
    ): Call<SearchResponse>

    // 📈 Pobiera dane do wykresu konkretnej kryptowaluty
    @GET("coins/{id}/market_chart")
    fun getMarketChart(
        @Path("id") id: String,
        @Query("vs_currency") vsCurrency: String,
        @Query("days") days: Int
    ): Call<MarketChartResponse>
}
