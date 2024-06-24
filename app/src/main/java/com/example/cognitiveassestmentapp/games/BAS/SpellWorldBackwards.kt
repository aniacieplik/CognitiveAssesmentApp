package com.example.cognitiveassestmentapp.games.BAS

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cognitiveassestmentapp.R

class SpellWorldBackwards : AppCompatActivity() {

    private lateinit var promptTextView: TextView
    private lateinit var inputEditText: EditText
    private lateinit var submitButton: Button
    private lateinit var resultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spell_world_backwards)

        promptTextView = findViewById(R.id.promptTextView)
        inputEditText = findViewById(R.id.inputEditText)
        submitButton = findViewById(R.id.submitButton)
        resultTextView = findViewById(R.id.resultTextView)

        submitButton.setOnClickListener {
            val userInput = inputEditText.text.toString().trim()
            val correctAnswer = "DLROW"

            val intent = Intent(this, RememberWordsInput::class.java)
            startActivity(intent)

            if (userInput.equals(correctAnswer, ignoreCase = true)) {
                resultTextView.text = "Correct! The word spelled backwards is '$correctAnswer'."
            } else {
                resultTextView.text = "Incorrect. Please try again."
            }
        }
    }
}