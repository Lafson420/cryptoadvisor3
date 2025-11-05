package com.example.cryptoadvisor3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "cryptoadvisor3"

        val buttonCrypto = findViewById<Button>(R.id.buttonCrypto)
        val buttonEducation = findViewById<Button>(R.id.buttonEducation)
        val buttonWallet = findViewById<Button>(R.id.buttonWallet)

        buttonCrypto.setOnClickListener {
            startActivity(Intent(this, CryptoListActivity::class.java))
        }

        buttonEducation.setOnClickListener {
            startActivity(Intent(this, EducationActivity::class.java))
        }

        buttonWallet.setOnClickListener {
            startActivity(Intent(this, WalletActivity::class.java))
        }

    }
}
