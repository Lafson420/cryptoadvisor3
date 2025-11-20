package com.example.cryptoadvisor3

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinGeckoApi {
    @GET("coins/{id}/market_chart")
    fun getCoinMarketChart(
        @Path("id") id: String,
        @Query("vs_currency") vsCurrency: String,
        @Query("days") days: Int
    ): Call<MarketChartResponse>
}
