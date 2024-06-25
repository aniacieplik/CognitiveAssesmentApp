package com.example.cognitiveassestmentapp.games.BAS

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.cognitiveassestmentapp.R

class RememberWordsInput : AppCompatActivity() {

    private lateinit var word1EditText: EditText
    private lateinit var word2EditText: EditText
    private lateinit var word3EditText: EditText
    private lateinit var submitButton: Button
    private val correctWords = setOf("APPLE", "TABLE", "PENNY")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remember_words_input)

        word1EditText = findViewById(R.id.word1EditText)
        word2EditText = findViewById(R.id.word2EditText)
        word3EditText = findViewById(R.id.word3EditText)
        submitButton = findViewById(R.id.submitButton)

        submitButton.setOnClickListener {
            val inputWords = setOf(
                word1EditText.text.toString().trim().uppercase(),
                word2EditText.text.toString().trim().uppercase(),
                word3EditText.text.toString().trim().uppercase()
            )

            val rememberPoints = inputWords.count { it in correctWords }

            val sharedPreferences = getSharedPreferences("CognitiveAssessmentApp", Context.MODE_PRIVATE)
            val totalRememberPoints = sharedPreferences.getInt("totalRememberPoints", 0) + rememberPoints

            with(sharedPreferences.edit()) {
                putInt("totalRememberPoints", totalRememberPoints)
                apply()
            }

            val intent = Intent(this, AnimalInput::class.java)
            startActivity(intent)
        }
    }
}
