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

/**
 * Activity for the "Animal Input" game where users are required to input as many animal names
 * as they can within a given time frame.
 */
class AnimalInput : AppCompatActivity() {

    private lateinit var timerTextView: TextView
    private lateinit var animalInputEditText: EditText
    private lateinit var startButton: Button
    private lateinit var resultTextView: TextView

    private var timer: CountDownTimer? = null

    /**
     * Called when the activity is first created. Initializes the UI elements and sets up
     * the start button's click listener.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down
     *                           then this Bundle contains the data it most recently supplied. Note: Otherwise it is null.
     */
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

    /**
     * Starts the countdown timer for the game. Updates the timer TextView each second and
     * handles the end of the timer.
     */
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

    /**
     * Calculates the points based on the number of animal names entered by the user.
     * Saves the total points to shared preferences.
     */
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

    /**
     * Navigates to the statistics activity to display the game results.
     */
    private fun navigateToStatistics() {
        val intent = Intent(this, StatisticsBAS::class.java)
        startActivity(intent)
    }

    /**
     * Called when the activity is destroyed. Cancels the timer if it is still running.
     */
    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }
}
