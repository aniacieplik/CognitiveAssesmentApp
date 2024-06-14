package com.example.cognitiveassestmentapp.games.TYM

import android.content.Context
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

        // Get the copied sentence from the intent
        val copiedSentence = intent.getStringExtra("copiedSentence")

        submitButton.setOnClickListener {
            val userInput = userInputEditText.text.toString().trim()

            var correctAnswers = 0
            if (userInput.equals(copiedSentence, ignoreCase = true)) {
                correctAnswers++
            }

            // Save statistics
            saveStatistics(correctAnswers, 1) // 1 is the total number of questions in this activity

            // Proceed to the next activity
            val intent = Intent(this, StatisticsTYM::class.java)
            startActivity(intent)
        }
    }

    private fun saveStatistics(correctAnswers: Int, totalQuestions: Int) {
        val sharedPreferences = getSharedPreferences("CognitiveAssessmentApp", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("sentenceAgainCorrectAnswers", correctAnswers)
        editor.putInt("totalSentenceAgainQuestions", totalQuestions)
        editor.apply()
    }
}
