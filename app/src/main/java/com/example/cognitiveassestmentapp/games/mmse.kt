package com.example.cognitiveassesmenttest.games

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cognitiveassesmenttest.R

class mmse : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mmse)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Find views
        val yearInput: EditText = findViewById(R.id.yearInput)
        val seasonInput: EditText = findViewById(R.id.seasonInput)
        val submitButton: Button = findViewById(R.id.submitButton)
        val resultTextView: TextView = findViewById(R.id.resultTextView)

        // Set submit button click listener
        submitButton.setOnClickListener {
            var score = 0

            // Example scoring logic (you'll need to add more detailed scoring for all questions)
            if (yearInput.text.toString() == "2024") score++
            if (seasonInput.text.toString().toLowerCase() == "summer") score++

            // Display score
            resultTextView.text = "Score: $score"
        }
    }
}
