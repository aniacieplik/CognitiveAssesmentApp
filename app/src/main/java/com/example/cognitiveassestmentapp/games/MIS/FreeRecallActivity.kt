package com.example.cognitiveassestmentapp.games.MIS

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cognitiveassestmentapp.R
import com.example.cognitiveassestmentapp.statistics.StatisticsMIS

class FreeRecallActivity : AppCompatActivity() {

    private val words = listOf(
        "Checkers" to "Game",
        "Saucer" to "Dish",
        "Telegram" to "Message",
        "Red Cross" to "Organisation"
    ).shuffled()

    private lateinit var wordEditTexts: List<EditText>
    private lateinit var hintTextViews: List<TextView>
    private lateinit var startButton: Button
    private lateinit var finishButton: Button
    private lateinit var nextButton: Button
    private lateinit var instructionTextView: TextView
    private lateinit var timerTextView: TextView
    private lateinit var countDownTimer: CountDownTimer
    private var guessedWords = mutableListOf<String>()
    private var points = 0
    private var finishButtonClickCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_freerecall)

        instructionTextView = findViewById(R.id.instruction_textview)
        timerTextView = findViewById(R.id.timertextview)
        startButton = findViewById(R.id.start_button)
        finishButton = findViewById(R.id.finish_button)
        nextButton = findViewById(R.id.next_button)
        nextButton.isEnabled = false

        instructionTextView.text =
            "Please state as many of the 4 words you can recall, you have a minute."

        wordEditTexts = listOf(
            findViewById(R.id.word1edittext),
            findViewById(R.id.word2edittext),
            findViewById(R.id.word3edittext),
            findViewById(R.id.word4edittext)
        )

        hintTextViews = listOf(
            findViewById(R.id.hint1textview),
            findViewById(R.id.hint2textview),
            findViewById(R.id.hint3textview),
            findViewById(R.id.hint4textview)
        )

        hintTextViews.forEach { textView ->
            textView.text = ""
        }

        wordEditTexts.forEach { editText ->
            editText.isEnabled = false
        }

        finishButton.isEnabled = false

        startButton.setOnClickListener {
            startButton.isEnabled = false
            startTimer()
            wordEditTexts.forEach { editText ->
                editText.isEnabled = true
            }
            finishButton.isEnabled = true
        }

        finishButton.setOnClickListener {
            calculatePoints()
            finishButtonClickCount++
            if (finishButtonClickCount == 2) {
                nextButton.isEnabled = true
            }
        }

        nextButton.setOnClickListener {
            val intent = Intent(this, StatisticsMIS::class.java)
            intent.putExtra("points", points)
            startActivity(intent)
            finish()
        }
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timerTextView.text = "Time remaining: ${millisUntilFinished / 1000}s"
            }

            override fun onFinish() {
                calculatePoints()
                finishButtonClickCount++
                if (finishButtonClickCount == 2) {
                    nextButton.isEnabled = true
                }
            }
        }.start()
    }

    private fun stopTimer() {
        countDownTimer.cancel()
    }

    private fun calculatePoints() {
        val correctWords = words.map { it.first }
        var allCorrect = true

        guessedWords.clear()

        wordEditTexts.forEachIndexed { index, editText ->
            val userAnswer = editText.text.toString()
            if (userAnswer in correctWords && userAnswer !in guessedWords) {
                guessedWords.add(userAnswer)
                points += 2
            }
        }

        wordEditTexts.forEachIndexed { index, editText ->
            val userAnswer = editText.text.toString()
            if (userAnswer !in correctWords) {
                allCorrect = false
                if (userAnswer.isEmpty()) {
                    val remainingWords = correctWords - guessedWords
                    val nextWord = remainingWords.getOrNull(index)
                    nextWord?.let {
                        val correctIndex = correctWords.indexOf(it)
                        hintTextViews[index].text = "Hint: ${words[correctIndex].second}"
                        hintTextViews[index].setTextColor(Color.RED)
                        points += 1
                    }
                }
            } else {
                val correctIndex = correctWords.indexOf(userAnswer)
                if (userAnswer !in guessedWords) {
                    hintTextViews[correctIndex].text = "Correct!"
                    hintTextViews[correctIndex].setTextColor(Color.GREEN)
                }
                editText.isEnabled = false
            }
        }

        stopTimer()
    }
}
