package com.example.cognitiveassestmentapp.games.TYM

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cognitiveassestmentapp.R

class Animals : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animals)

        val animal1Input: EditText = findViewById(R.id.animal1Input)
        val animal2Input: EditText = findViewById(R.id.animal2Input)
        val animal3Input: EditText = findViewById(R.id.animal3Input)
        val animal4Input: EditText = findViewById(R.id.animal4Input)
        val animal5Input: EditText = findViewById(R.id.animal5Input)
        val submitAnimalsButton: Button = findViewById(R.id.submitAnimalsButton)

        val validAnimals = setOf("swan", "salmon", "scorpion", "sea lion", "seal", "skunk", "snail", "snake", "spider", "squid", "squirrel", "stingray", "sparrow", "sheep", "shark", "snow leopard", "salamander", "sardine", "scallop", "seahorse", "shrimp", "sloth", "starfish", "serval")

        submitAnimalsButton.setOnClickListener {
            val animals = listOf(
                animal1Input.text.toString().trim().lowercase(),
                animal2Input.text.toString().trim().lowercase(),
                animal3Input.text.toString().trim().lowercase(),
                animal4Input.text.toString().trim().lowercase(),
                animal5Input.text.toString().trim().lowercase()
            )

            var correctAnswers = 0
            animals.forEach { if (it in validAnimals) correctAnswers++ }

            // Save statistics
            saveStatistics(correctAnswers, animals.size)

            val intent = Intent(this, ComparisonQuestions::class.java)
            startActivity(intent)
        }
    }

    private fun saveStatistics(correctAnswers: Int, totalQuestions: Int) {
        val sharedPreferences = getSharedPreferences("CognitiveAssessmentApp", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("animalsCorrectAnswers", correctAnswers)
        editor.putInt("totalAnimalsQuestions", totalQuestions)
        editor.apply()
    }
}
