package com.example.cryptoadvisor3

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class CryptoAdapter(private val context: Context, private val cryptoList: List<CoinListItem>) : BaseAdapter() {

    override fun getCount(): Int = cryptoList.size

    override fun getItem(position: Int): Any = cryptoList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_crypto, parent, false)

        val coin = cryptoList[position]

        val nameText = view.findViewById<TextView>(R.id.textName)
        val priceText = view.findViewById<TextView>(R.id.textPrice)
        val iconView = view.findViewById<ImageView>(R.id.imageIcon)

        nameText.text = coin.name
        priceText.text = String.format("%.2f USD", coin.current_price)

        nameText.setTextColor(android.graphics.Color.WHITE)
        priceText.setTextColor(android.graphics.Color.WHITE)

        Glide.with(context)
            .load(coin.image)
            .into(iconView)

        return view
    }
}
