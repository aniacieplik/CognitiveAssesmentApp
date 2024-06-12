package com.example.cognitiveassestmentapp.games.TYM

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cognitiveassestmentapp.R

class MathQuestions : AppCompatActivity() {
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

            if (answer1 == 16 && answer2 == 33 && answer3 == 48 && answer4 == 2) {
                Toast.makeText(this, "Correct! Well done!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Animals::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Incorrect. Please try again.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}