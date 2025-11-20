package com.example.cryptoadvisor3

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class WalletAdapter(private val context: Context, private val walletList: List<WalletItem>) : BaseAdapter() {

    override fun getCount(): Int = walletList.size

    override fun getItem(position: Int): Any = walletList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_wallet, parent, false)

        val coinImage = view.findViewById<ImageView>(R.id.coinImage)
        val coinName = view.findViewById<TextView>(R.id.coinName)
        val coinAmount = view.findViewById<TextView>(R.id.coinAmount)
        val coinValue = view.findViewById<TextView>(R.id.coinValue)

        val item = walletList[position]

        coinName.text = "${item.name} (${item.symbol.uppercase()})"
        coinAmount.text = "Ilość: ${item.amount}"
        coinValue.text = String.format("%.2f USD", item.amount * item.valueUsd)

        // Ładowanie ikony z CoinGecko (bez imageId)
        val iconUrl = "https://assets.coingecko.com/coins/images/${item.id}.png"
        Glide.with(context)
            .load(iconUrl)
            .placeholder(R.drawable.ic_placeholder_crypto)
            .into(coinImage)

        return view
    }
}
