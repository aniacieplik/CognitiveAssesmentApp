package com.example.cognitiveassestmentapp.games.TYM

import android.content.Context
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

            var correctAnswers = 0
            if (answer1 == 16) correctAnswers++
            if (answer2 == 33) correctAnswers++
            if (answer3 == 48) correctAnswers++
            if (answer4 == 2) correctAnswers++

            // Save statistics
            saveStatistics(correctAnswers, 4)

            val intent = Intent(this, Animals::class.java)
            startActivity(intent)
        }
    }

    private fun saveStatistics(correctAnswers: Int, totalQuestions: Int) {
        val sharedPreferences = getSharedPreferences("CognitiveAssessmentApp", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("mathCorrectAnswers", correctAnswers)
        editor.putInt("totalMathQuestions", totalQuestions)
        editor.apply()
    }
}
