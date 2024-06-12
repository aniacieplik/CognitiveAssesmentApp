package com.example.cognitiveassestmentapp.games.TYM

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cognitiveassestmentapp.R

class Questions : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)

        val primeMinisterInput: EditText = findViewById(R.id.primeMinisterInput)
        val ww2YearInput: EditText = findViewById(R.id.ww2YearInput)
        val submitQuestionsButton: Button = findViewById(R.id.submitQuestionsButton)

        submitQuestionsButton.setOnClickListener {
            val primeMinister = primeMinisterInput.text.toString()
            val ww2Year = ww2YearInput.text.toString()

            if (primeMinister == "Donald Tusk" && ww2Year == "1939") {
                Toast.makeText(this, "Correct! Well done!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MathQuestions::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Incorrect. Please try again.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}