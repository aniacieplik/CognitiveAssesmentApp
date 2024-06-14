package com.example.cognitiveassestmentapp.games.TYM

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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

            var correctAnswers = 0
            if (copiedText.equals(correctSentence, ignoreCase = true)) {
                correctAnswers++
            }

            // Save statistics
            saveStatistics(correctAnswers, 1)

            val intent = Intent(this, GeneralQuestions::class.java)
            startActivity(intent)
        }
    }

    private fun saveStatistics(correctAnswers: Int, totalQuestions: Int) {
        val sharedPreferences = getSharedPreferences("CognitiveAssessmentApp", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("copySentenceCorrectAnswers", correctAnswers)
        editor.putInt("totalCopySentenceQuestions", totalQuestions)
        editor.apply()
    }
}
