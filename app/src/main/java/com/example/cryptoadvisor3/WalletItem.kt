package com.example.cryptoadvisor3

data class WalletItem(
    val id: String,        // tekstowe ID monety, np. "bitcoin"
    val name: String,
    val symbol: String,
    val amount: Double,
    val valueUsd: Double
)
