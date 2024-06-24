package com.example.cognitiveassestmentapp.games.MIS

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cognitiveassestmentapp.R


class DistractorActivity : AppCompatActivity() {

    private var currentNumber = 100
    private val targetNumber = 37
    private var incorrectGuesses = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_distractor)

        val instructionTextView: TextView = findViewById(R.id.instruction_textview)
        val hintTextView: TextView = findViewById(R.id.hint_textview)
        val answerEditText: EditText = findViewById(R.id.answer_edittext)
        val submitButton: Button = findViewById(R.id.submit_button)
        val finishButton: Button = findViewById(R.id.finishing_button)

        updateHintText()

        submitButton.setOnClickListener {
            val answerText = answerEditText.text.toString().trim()
            if (answerText.isEmpty()) {
                showToast("Please enter an answer")
            } else {
                val answer = answerText.toInt()
                if (answer == currentNumber - 7) {
                    showToast("Correct!")
                    currentNumber -= 7
                    if (currentNumber >= targetNumber) {
                        updateHintText()
                        answerEditText.setText("")
                        answerEditText.hint = "Enter your answer"
                        hintTextView.visibility = TextView.GONE
                        incorrectGuesses = 0

                        if (currentNumber == targetNumber) {
                            finishButton.isEnabled = true
                            finishButton.setBackgroundColor(resources.getColor(R.color.pink))
                        }
                    } else {
                        finish()
                    }
                } else {
                    showToast("Incorrect! Try again.")
                    hintTextView.visibility = TextView.VISIBLE
                    incorrectGuesses++

                    if (incorrectGuesses >= 3) {
                        showAnswer()
                    }
                }
            }
        }

        finishButton.setOnClickListener {
            startActivity(Intent(this, FreeRecallActivity::class.java))
            finish()
        }
    }

    private fun updateHintText() {
        val hintTextView: TextView = findViewById(R.id.hint_textview)
        hintTextView.text = "Hint: $currentNumber - 7"
    }

    private fun showAnswer() {
        val hintTextView: TextView = findViewById(R.id.hint_textview)
        hintTextView.text = "Answer: ${currentNumber - 7}"
        hintTextView.visibility = TextView.VISIBLE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
