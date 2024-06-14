package com.example.cognitiveassestmentapp.games.TYM

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.cognitiveassestmentapp.R
import com.example.cognitiveassestmentapp.statistics.StatisticsTYM

class SentenceAgain : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sentence_again)

        val userInputEditText = findViewById<EditText>(R.id.userInputEditText)

        val submitButton = findViewById<Button>(R.id.submitButton)
        submitButton.setOnClickListener {
            val userInput = userInputEditText.text.toString().trim()

            if (userInput.equals("GOOD CITIZENS ALWAYS WEAR STOUT SHOES", ignoreCase = true)) {
                val intent = Intent(this, StatisticsTYM::class.java)
                startActivity(intent)
            } else {
                userInputEditText.error = "Input does not match the required sentence."
            }
        }
    }
}