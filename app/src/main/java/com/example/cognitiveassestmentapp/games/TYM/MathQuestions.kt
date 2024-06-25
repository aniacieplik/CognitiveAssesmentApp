package com.example.cognitiveassestmentapp.games.TYM

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.cognitiveassestmentapp.R

/**
 * Activity for the "Math Questions" game where users solve basic math equations.
 */
class MathQuestions : AppCompatActivity() {
    /**
     * Called when the activity is first created. Initializes the UI elements and sets up
     * the submit button's click listener.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down
     *                           then this Bundle contains the data it most recently supplied. Note: Otherwise it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_math_questions)

        val equation1Input: EditText = findViewById(R.id.equation1Input)
        val equation2Input: EditText = findViewById(R.id.equation2Input)
        val equation3Input: EditText = findViewById(R.id.equation3Input)
        val equation4Input: EditText = findViewById(R.id.equation4Input)
        val submitMathButton: Button = findViewById(R.id.submitMathButton)

        submitMathButton.setOnClickListener {
            val answer1 = equation1Input.text.toString().toIntOrNull()
            val answer2 = equation2Input.text.toString().toIntOrNull()
            val answer3 = equation3Input.text.toString().toIntOrNull()
            val answer4 = equation4Input.text.toString().toIntOrNull()

            var correctAnswers = 0
            if (answer1 == 16) correctAnswers++
            if (answer2 == 33) correctAnswers++
            if (answer3 == 48) correctAnswers++
            if (answer4 == 2) correctAnswers++

            saveStatistics(correctAnswers, 4)

            val intent = Intent(this, Animals::class.java)
            startActivity(intent)
        }
    }

    /**
     * Saves the statistics of the "Math Questions" game to shared preferences.
     *
     * @param correctAnswers The number of correct answers provided by the user.
     * @param totalQuestions The total number of questions asked in the game.
     */
    private fun saveStatistics(correctAnswers: Int, totalQuestions: Int) {
        val sharedPreferences = getSharedPreferences("CognitiveAssessmentApp", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("mathCorrectAnswers", correctAnswers)
        editor.putInt("totalMathQuestions", totalQuestions)
        editor.apply()
    }
}
