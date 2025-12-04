package com.example.cryptoadvisor3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var logoutButton: Button
    private lateinit var cryptoButton: Button
    private lateinit var walletButton: Button
    private lateinit var educationButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_main)

        logoutButton = findViewById(R.id.buttonLogout)
        cryptoButton = findViewById(R.id.buttonCrypto)
        walletButton = findViewById(R.id.buttonWallet)
        educationButton = findViewById(R.id.buttonEducation)

        logoutButton.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        cryptoButton.setOnClickListener {
            startActivity(Intent(this, CryptoListActivity::class.java))
        }

        walletButton.setOnClickListener {
            startActivity(Intent(this, WalletActivity::class.java))
        }

        educationButton.setOnClickListener {
            startActivity(Intent(this, EducationActivity::class.java))
        }
    }
}
