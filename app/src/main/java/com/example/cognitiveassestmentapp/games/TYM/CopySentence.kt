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
 * Activity that handles the "Copy Sentence" game where the user is asked to copy a given sentence.
 */
class CopySentence : AppCompatActivity() {

    /**
     * Called when the activity is first created. Initializes the UI elements and sets up
     * the submit button's click listener.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down
     *                           then this Bundle contains the data it most recently supplied. Note: Otherwise it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_copy_sentence)

        val copySentenceInput: EditText = findViewById(R.id.copySentenceInput)
        val submitSentenceButton: Button = findViewById(R.id.submitSentenceButton)

        submitSentenceButton.setOnClickListener {
            val copiedText = copySentenceInput.text.toString()
            val correctSentence = "GOOD CITIZENS ALWAYS WEAR STOUT SHOES"

            var correctAnswers = 0
            if (copiedText.equals(correctSentence, ignoreCase = true)) {
                correctAnswers++
            }

            saveStatistics(correctAnswers, 1)

            val intent = Intent(this, GeneralQuestions::class.java)
            startActivity(intent)
        }
    }

    /**
     * Saves the statistics of the "Copy Sentence" game to shared preferences.
     *
     * @param correctAnswers The number of correct answers provided by the user.
     * @param totalQuestions The total number of questions asked in the game.
     */
    private fun saveStatistics(correctAnswers: Int, totalQuestions: Int) {
        val sharedPreferences = getSharedPreferences("CognitiveAssessmentApp", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("copySentenceCorrectAnswers", correctAnswers)
        editor.putInt("totalCopySentenceQuestions", totalQuestions)
        editor.apply()
    }
}
