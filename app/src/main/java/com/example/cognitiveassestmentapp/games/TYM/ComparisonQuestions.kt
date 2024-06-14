package com.example.cognitiveassestmentapp.games.TYM

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cognitiveassestmentapp.R
import com.example.cognitiveassestmentapp.games.statistics.StatisticsTYM

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

            if (carrotPotatoAnswer == "vegetable" && lionWolfAnswer == "animal") {
                Toast.makeText(this, "Correct! Well done!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, StatisticsTYM::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Incorrect. Please try again.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}