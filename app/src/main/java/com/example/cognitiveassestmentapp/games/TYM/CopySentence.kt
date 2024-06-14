package com.example.cognitiveassestmentapp.games.TYM

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cognitiveassestmentapp.R

class CopySentence : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_copy_sentence)

        val copySentenceInput: EditText = findViewById(R.id.copySentenceInput)
        val submitSentenceButton: Button = findViewById(R.id.submitSentenceButton)

        submitSentenceButton.setOnClickListener {
            val copiedText = copySentenceInput.text.toString()
            val correctSentence = "GOOD CITIZENS ALWAYS WEAR STOUT SHOES"

            if (copiedText.equals(correctSentence, ignoreCase = true) ) {
                Toast.makeText(this, "Correct! Well done!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Questions::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Incorrect. Please try again.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}