package com.example.cognitiveassestmentapp.games.BAS

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cognitiveassestmentapp.R

class RememberWords : AppCompatActivity() {

    private lateinit var rememberWordsTextView: TextView
    private lateinit var wordsTextView: TextView
    private lateinit var nextButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remember_words)

        rememberWordsTextView = findViewById(R.id.rememberWordsTextView)
        wordsTextView = findViewById(R.id.wordsTextView)
        nextButton = findViewById(R.id.nextButton)

        val words = listOf("Apple", "Table", "Penny")
        wordsTextView.text = words.joinToString(", ")

        nextButton.setOnClickListener {
            val intent = Intent(this, SpellWorldBackwards::class.java)
            startActivity(intent)
        }
    }
}