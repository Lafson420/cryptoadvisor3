package com.example.cryptoadvisor3

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinGeckoApi {

    // Pobiera listę topowych kryptowalut z aktualnymi cenami
    @GET("coins/markets")
    fun getTopCoins(
        @Query("vs_currency") currency: String = "usd",
        @Query("order") order: String = "market_cap_desc",
        @Query("per_page") perPage: Int = 50,
        @Query("page") page: Int = 1,
        @Query("sparkline") sparkline: Boolean = false
    ): Call<List<CoinListItem>>

    // Wyszukuje kryptowaluty po nazwie (bez cen)
    @GET("search")
    fun searchCoins(
        @Query("query") query: String
    ): Call<SearchResponse>
}
