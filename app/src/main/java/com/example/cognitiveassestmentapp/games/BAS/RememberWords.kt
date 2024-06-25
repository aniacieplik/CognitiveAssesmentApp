package com.example.cognitiveassestmentapp.games.BAS

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cognitiveassestmentapp.R

/**
 * Activity for the "Remember Words" game where users are presented with a list of words to remember.
 */
class RememberWords : AppCompatActivity() {

    private lateinit var rememberWordsTextView: TextView
    private lateinit var wordsTextView: TextView
    private lateinit var nextButton: Button

    /**
     * Called when the activity is first created. Initializes the UI elements and sets up
     * the next button's click listener.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down
     *                           then this Bundle contains the data it most recently supplied. Note: Otherwise it is null.
     */
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
