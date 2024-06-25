package com.example.cognitiveassestmentapp.games.BAS

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.cognitiveassestmentapp.R

class RememberWordsInput : AppCompatActivity() {

    private lateinit var word1EditText: EditText
    private lateinit var word2EditText: EditText
    private lateinit var word3EditText: EditText
    private lateinit var submitButton: Button
    private var spellPoints: Int = 0
    private var rememberPoints: Int = 0
    private val correctWords = listOf("APPLE", "TABLE", "PENNY")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remember_words_input)

        spellPoints = intent.getIntExtra("SPELL_POINTS", 0)

        word1EditText = findViewById(R.id.word1EditText)
        word2EditText = findViewById(R.id.word2EditText)
        word3EditText = findViewById(R.id.word3EditText)
        submitButton = findViewById(R.id.submitButton)

        submitButton.setOnClickListener {
            val word1Input = word1EditText.text.toString().trim().uppercase()
            val word2Input = word2EditText.text.toString().trim().uppercase()
            val word3Input = word3EditText.text.toString().trim().uppercase()

            rememberPoints = listOf(word1Input, word2Input, word3Input).count { it in correctWords }

            val intent = Intent(this, AnimalInput::class.java).apply {
                putExtra("SPELL_POINTS", spellPoints)
                putExtra("REMEMBER_POINTS", rememberPoints)
            }
            startActivity(intent)
        }
    }
}
