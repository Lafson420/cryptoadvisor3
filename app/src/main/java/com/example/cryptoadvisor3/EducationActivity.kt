package com.example.cryptoadvisor3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EducationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_education)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Edukacja"

        val topics = listOf(
            EducationTopic("Co to jest Bitcoin?", "Wyjaśnienie czym jest Bitcoin."),
            EducationTopic("Jak działa blockchain?", "Zasady działania technologii blockchain."),
            EducationTopic("Bezpieczeństwo portfela", "Jak chronić swoje środki?"),
            EducationTopic("Stablecoiny", "Czym są i jak działają?"),
            EducationTopic("Altcoiny", "Alternatywne kryptowaluty – przykłady i zastosowania.")
        )

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewEducation)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = EducationAdapter(topics)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
