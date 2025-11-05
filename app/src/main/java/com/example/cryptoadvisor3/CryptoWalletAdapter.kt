package com.example.cryptoadvisor3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CryptoWalletAdapter(private val cryptoList: List<CryptoItem>) :
    RecyclerView.Adapter<CryptoWalletAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textName: TextView = view.findViewById(R.id.textCryptoName)
        val textAmount: TextView = view.findViewById(R.id.textCryptoAmount)
        val textValue: TextView = view.findViewById(R.id.textCryptoValue)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_crypto_wallet, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val crypto = cryptoList[position]
        holder.textName.text = crypto.name
        holder.textAmount.text = String.format("%.4f", crypto.amount)
        holder.textValue.text = String.format("%.2f USD", crypto.valueUSD)
    }

    override fun getItemCount(): Int = cryptoList.size
}
