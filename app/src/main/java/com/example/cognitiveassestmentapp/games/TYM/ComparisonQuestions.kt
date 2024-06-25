package com.example.cognitiveassestmentapp.games.TYM

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cognitiveassestmentapp.R
import com.example.cognitiveassestmentapp.statistics.StatisticsTYM

/**
 * Activity for the "Comparison Questions" game where users answer comparison-based questions.
 */
class ComparisonQuestions : AppCompatActivity() {
    /**
     * Called when the activity is first created. Initializes the UI elements and sets up
     * the submit button's click listener.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down
     *                           then this Bundle contains the data it most recently supplied. Note: Otherwise it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comparison_questions)

        val carrotPotatoInput: EditText = findViewById(R.id.carrotPotatoInput)
        val lionWolfInput: EditText = findViewById(R.id.lionWolfInput)
        val submitComparisonButton: Button = findViewById(R.id.submitComparisonButton)

        carrotPotatoInput.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                Toast.makeText(this, "Hint: Answer should be 1 word", Toast.LENGTH_SHORT).show()
            }
        }

        lionWolfInput.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                Toast.makeText(this, "Hint: Answer should be 1 word", Toast.LENGTH_SHORT).show()
            }
        }

        submitComparisonButton.setOnClickListener {
            val carrotPotatoAnswer = carrotPotatoInput.text.toString().trim().lowercase()
            val lionWolfAnswer = lionWolfInput.text.toString().trim().lowercase()

            var correctAnswers = 0
            if (carrotPotatoAnswer == "vegetable" || carrotPotatoAnswer == "vegetables") correctAnswers++
            if (lionWolfAnswer == "animal" || lionWolfAnswer == "animals") correctAnswers++

            saveStatistics(correctAnswers, 2)

            val intent = Intent(this, SentenceAgain::class.java)
            startActivity(intent)
        }
    }

    /**
     * Saves the statistics of the "Comparison Questions" game to shared preferences.
     *
     * @param correctAnswers The number of correct answers provided by the user.
     * @param totalQuestions The total number of questions asked in the game.
     */
    private fun saveStatistics(correctAnswers: Int, totalQuestions: Int) {
        val sharedPreferences = getSharedPreferences("CognitiveAssessmentApp", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("comparisonCorrectAnswers", correctAnswers)
        editor.putInt("totalComparisonQuestions", totalQuestions)
        editor.apply()
    }
}
