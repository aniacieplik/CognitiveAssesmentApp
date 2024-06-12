package com.example.cognitiveassestmentapp.games.TYM

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

        val validAnimals = setOf("swan", "salmon", "scorpion", "sea lion", "seal", "skunk", "snail", "snake", "spider", "squid", "squirrel", "stingray", "sparrow", "ship", "shark")

        submitAnimalsButton.setOnClickListener {
            val animals = listOf(
                animal1Input.text.toString().trim().lowercase(),
                animal2Input.text.toString().trim().lowercase(),
                animal3Input.text.toString().trim().lowercase(),
                animal4Input.text.toString().trim().lowercase(),
                animal5Input.text.toString().trim().lowercase()
            )

            if (animals.all { it in validAnimals }) {
                Toast.makeText(this, "Correct! Well done!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ComparisonQuestions::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Incorrect. Please try again.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}