package com.example.cryptoadvisor3

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class CryptoListAdapter(private val context: Context, private val cryptoList: List<CryptoCurrency>) : BaseAdapter() {

    override fun getCount(): Int = cryptoList.size

    override fun getItem(position: Int): Any = cryptoList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_crypto, parent, false)

        val crypto = cryptoList[position]
        val iconImageView = view.findViewById<ImageView>(R.id.imageIcon)
        val nameTextView = view.findViewById<TextView>(R.id.textName)
        val priceTextView = view.findViewById<TextView>(R.id.textPrice)

        nameTextView.text = crypto.name
        priceTextView.text = String.format("%.2f USD", crypto.current_price)

        Glide.with(context)
            .load(crypto.image)
            .into(iconImageView)

        return view
    }
}
