package com.example.cognitiveassestmentapp.games.TYM

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.cognitiveassestmentapp.R
import java.util.Calendar

/**
 * Activity for the "General Questions" game where users answer general knowledge questions.
 */
class GeneralQuestions : AppCompatActivity() {
    /**
     * Called when the activity is first created. Initializes the UI elements and sets up
     * the submit button's click listener.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down
     *                           then this Bundle contains the data it most recently supplied. Note: Otherwise it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general_questions)

        val primeMinisterInput: EditText = findViewById(R.id.primeMinisterInput)
        val ww2YearSpinner: Spinner = findViewById(R.id.ww2YearSpinner)
        val submitQuestionsButton: Button = findViewById(R.id.submitQuestionsButton)

        val years = (1900..Calendar.getInstance().get(Calendar.YEAR)).toList().reversed()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, years)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        ww2YearSpinner.adapter = adapter

        submitQuestionsButton.setOnClickListener {
            val primeMinister = primeMinisterInput.text.toString()
            val ww2Year = ww2YearSpinner.selectedItem.toString()

            var correctAnswers = 0
            if (primeMinister == "Donald Tusk") correctAnswers++
            if (ww2Year == "1939") correctAnswers++

            saveStatistics(correctAnswers, 2)

            val intent = Intent(this, MathQuestions::class.java)
            startActivity(intent)
        }
    }

    /**
     * Saves the statistics of the "General Questions" game to shared preferences.
     *
     * @param correctAnswers The number of correct answers provided by the user.
     * @param totalQuestions The total number of questions asked in the game.
     */
    private fun saveStatistics(correctAnswers: Int, totalQuestions: Int) {
        val sharedPreferences = getSharedPreferences("CognitiveAssessmentApp", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("questionsCorrectAnswers", correctAnswers)
        editor.putInt("totalGeneralQuestions", totalQuestions)
        editor.apply()
    }
}
