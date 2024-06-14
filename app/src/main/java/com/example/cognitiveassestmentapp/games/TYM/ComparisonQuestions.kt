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

class ComparisonQuestions : AppCompatActivity() {
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
            if (carrotPotatoAnswer == "vegetable") correctAnswers++
            if (lionWolfAnswer == "animal") correctAnswers++

            // Save statistics
            saveStatistics(correctAnswers, 2) // 2 is the total number of comparison questions

            val intent = Intent(this, SentenceAgain::class.java)
            startActivity(intent)
        }
    }

    private fun saveStatistics(correctAnswers: Int, totalQuestions: Int) {
        val sharedPreferences = getSharedPreferences("CognitiveAssessmentApp", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("comparisonCorrectAnswers", correctAnswers)
        editor.putInt("totalComparisonQuestions", totalQuestions)
        editor.apply()
    }
}
