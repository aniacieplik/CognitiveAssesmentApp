package com.example.cognitiveassestmentapp.games.BAS
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cognitiveassestmentapp.R

class RememberWordsInput : AppCompatActivity() {

    private lateinit var promptTextView: TextView
    private lateinit var word1EditText: EditText
    private lateinit var word2EditText: EditText
    private lateinit var word3EditText: EditText
    private lateinit var submitButton: Button
    private lateinit var resultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remember_words_input)

        promptTextView = findViewById(R.id.promptTextView)
        word1EditText = findViewById(R.id.word1EditText)
        word2EditText = findViewById(R.id.word2EditText)
        word3EditText = findViewById(R.id.word3EditText)
        submitButton = findViewById(R.id.submitButton)
        resultTextView = findViewById(R.id.resultTextView)

        submitButton.setOnClickListener {
            val word1Input = word1EditText.text.toString().trim().uppercase()
            val word2Input = word2EditText.text.toString().trim().uppercase()
            val word3Input = word3EditText.text.toString().trim().uppercase()

            val correctWords = listOf("APPLE", "TABLE", "PENNY")

            val result = listOf(word1Input, word2Input, word3Input).all { it in correctWords }

            val intent = Intent(this, AnimalInput::class.java)
            startActivity(intent)

            if (result) {
                resultTextView.text = "Correct! You remembered all the words."
            } else {
                resultTextView.text = "Incorrect. Please try again."
            }
        }
    }
}