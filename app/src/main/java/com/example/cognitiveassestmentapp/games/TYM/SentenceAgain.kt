package com.example.cognitiveassestmentapp.games.TYM

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.cognitiveassestmentapp.R
import com.example.cognitiveassestmentapp.statistics.StatisticsTYM

/**
 * Activity for the "Sentence Again" game where users re-enter a previously copied sentence.
 */
class SentenceAgain : AppCompatActivity() {
    /**
     * Called when the activity is first created. Initializes the UI elements and sets up
     * the submit button's click listener.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down
     *                           then this Bundle contains the data it most recently supplied. Note: Otherwise it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sentence_again)

        val userInputEditText = findViewById<EditText>(R.id.userInputEditText)
        val submitButton = findViewById<Button>(R.id.submitButton)

        val copiedSentence = intent.getStringExtra("copiedSentence")

        submitButton.setOnClickListener {
            val userInput = userInputEditText.text.toString().trim()

            var correctAnswers = 0
            if (userInput.equals(copiedSentence, ignoreCase = true)) {
                correctAnswers++
            }

            saveStatistics(correctAnswers, 1)

            val intent = Intent(this, StatisticsTYM::class.java)
            startActivity(intent)
        }
    }

    /**
     * Saves the statistics of the "Sentence Again" game to shared preferences.
     *
     * @param correctAnswers The number of correct answers provided by the user.
     * @param totalQuestions The total number of questions asked in the game.
     */
    private fun saveStatistics(correctAnswers: Int, totalQuestions: Int) {
        val sharedPreferences = getSharedPreferences("CognitiveAssessmentApp", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("sentenceAgainCorrectAnswers", correctAnswers)
        editor.putInt("totalSentenceAgainQuestions", totalQuestions)
        editor.apply()
    }
}
