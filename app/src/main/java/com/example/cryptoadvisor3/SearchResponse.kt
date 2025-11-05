package com.example.cryptoadvisor3

data class SearchResponse(
    val coins: List<SearchCoin>
)

data class SearchCoin(
    val id: String,
    val name: String,
    val symbol: String,
    val thumb: String,
    val large: String? = null // dodajemy = null dla bezpieczeństwa
)
