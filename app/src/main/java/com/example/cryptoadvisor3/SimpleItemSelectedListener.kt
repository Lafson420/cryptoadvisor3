package com.example.cryptoadvisor3

import android.view.View
import android.widget.AdapterView

class SimpleItemSelectedListener(private val onSelect: (Any) -> Unit) :
    AdapterView.OnItemSelectedListener {
    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
        onSelect(parent.getItemAtPosition(position))
    }

    override fun onNothingSelected(parent: AdapterView<*>) {}
}
