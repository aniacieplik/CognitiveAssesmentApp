package com.example.cognitiveassestmentapp.games.BAS

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cognitiveassestmentapp.R
import com.example.cognitiveassestmentapp.statistics.StatisticsBAS

class AnimalInput : AppCompatActivity() {

    private lateinit var timerTextView: TextView
    private lateinit var animalInputEditText: EditText
    private lateinit var startButton: Button
    private lateinit var resultTextView: TextView

    private var timer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animal_input)

        timerTextView = findViewById(R.id.timerTextView)
        animalInputEditText = findViewById(R.id.animalInputEditText)
        startButton = findViewById(R.id.startButton)
        resultTextView = findViewById(R.id.resultTextView)

        startButton.setOnClickListener {
            startButton.isEnabled = false
            animalInputEditText.isEnabled = true
            animalInputEditText.text.clear()
            animalInputEditText.requestFocus()
            startTimer()
        }
    }

    private fun startTimer() {
        timer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsLeft = millisUntilFinished / 1000
                timerTextView.text = "Time left: ${secondsLeft}s"
            }

            override fun onFinish() {
                animalInputEditText.isEnabled = false
                startButton.isEnabled = true
                timerTextView.text = "Time left: 0s"
                calculatePoints()
                navigateToStatistics()
            }
        }.start()
    }

    private fun calculatePoints() {
        val animals = animalInputEditText.text.toString().trim()
        val animalList = animals.split("\\s+".toRegex()).filter { it.isNotEmpty() }
        val animalPoints = animalList.size

        val sharedPreferences = getSharedPreferences("CognitiveAssessmentApp", Context.MODE_PRIVATE)
        val totalAnimalPoints = sharedPreferences.getInt("totalAnimalPoints", 0) + animalPoints

        with(sharedPreferences.edit()) {
            putInt("totalAnimalPoints", totalAnimalPoints)
            apply()
        }
    }

    private fun navigateToStatistics() {
        val intent = Intent(this, StatisticsBAS::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }
}
