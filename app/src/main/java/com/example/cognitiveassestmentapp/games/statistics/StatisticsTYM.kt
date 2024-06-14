package com.example.cognitiveassestmentapp.games.statistics

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cognitiveassestmentapp.R

class StatisticsTYM : AppCompatActivity() {

    private lateinit var correctAnswersTextView: TextView
    private lateinit var totalQuestionsTextView: TextView
    private lateinit var animalsCorrectAnswersTextView: TextView
    private lateinit var totalAnimalsQuestionsTextView: TextView
    private lateinit var mathCorrectAnswersTextView: TextView
    private lateinit var totalMathQuestionsTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics_tym)

        correctAnswersTextView = findViewById(R.id.correctAnswersTextView)
        totalQuestionsTextView = findViewById(R.id.totalQuestionsTextView)
        animalsCorrectAnswersTextView = findViewById(R.id.animalsCorrectAnswersTextView)
        totalAnimalsQuestionsTextView = findViewById(R.id.totalAnimalsQuestionsTextView)
        mathCorrectAnswersTextView = findViewById(R.id.mathCorrectAnswersTextView)
        totalMathQuestionsTextView = findViewById(R.id.totalMathQuestionsTextView)

        // Get statistics from shared preferences
        val sharedPreferences = getSharedPreferences("CognitiveAssessmentApp", Context.MODE_PRIVATE)
        val correctAnswers = sharedPreferences.getInt("correctAnswers", 0)
        val totalQuestions = sharedPreferences.getInt("totalQuestions", 0)
        val animalsCorrectAnswers = sharedPreferences.getInt("animalsCorrectAnswers", 0)
        val totalAnimalsQuestions = sharedPreferences.getInt("totalAnimalsQuestions", 5)
        val mathCorrectAnswers = sharedPreferences.getInt("mathCorrectAnswers", 0)
        val totalMathQuestions = sharedPreferences.getInt("totalMathQuestions", 4)

        displayStatistics(correctAnswers, totalQuestions, animalsCorrectAnswers, totalAnimalsQuestions, mathCorrectAnswers, totalMathQuestions)
    }

    private fun displayStatistics(correctAnswers: Int, totalQuestions: Int, animalsCorrectAnswers: Int, totalAnimalsQuestions: Int, mathCorrectAnswers: Int, totalMathQuestions: Int) {
        correctAnswersTextView.text = "Correct Answers: $correctAnswers"
        totalQuestionsTextView.text = "Total Questions: $totalQuestions"
        animalsCorrectAnswersTextView.text = "Correct Answers for Animals: $animalsCorrectAnswers"
        totalAnimalsQuestionsTextView.text = "Total Animal Questions: $totalAnimalsQuestions"
        mathCorrectAnswersTextView.text = "Correct Answers for Math: $mathCorrectAnswers"
        totalMathQuestionsTextView.text = "Total Math Questions: $totalMathQuestions"
    }
}
