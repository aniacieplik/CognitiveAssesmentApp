package com.example.cognitiveassestmentapp.games.BAS

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.cognitiveassestmentapp.R

class SpellWorldBackwards : AppCompatActivity() {

    private lateinit var inputEditText: EditText
    private lateinit var submitButton: Button
    private var spellPoints: Int = 0
    private val correctAnswer = "DLROW"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spell_world_backwards)

        inputEditText = findViewById(R.id.inputEditText)
        submitButton = findViewById(R.id.submitButton)

        submitButton.setOnClickListener {
            val userInput = inputEditText.text.toString().trim().uppercase()

            spellPoints = userInput.indices.count { i -> i < correctAnswer.length && userInput[i] == correctAnswer[i] }

            val intent = Intent(this, RememberWordsInput::class.java).apply {
                putExtra("SPELL_POINTS", spellPoints)
            }
            startActivity(intent)
        }
    }
}
