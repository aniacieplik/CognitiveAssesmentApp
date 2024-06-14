package com.example.cognitiveassestmentapp.statistics

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cognitiveassestmentapp.R

class StatisticsTYM : AppCompatActivity() {

    private lateinit var basicCorrectAnswersTextView: TextView
    private lateinit var totalBasicQuestionsTextView: TextView
    private lateinit var copySentenceCorrectAnswersTextView: TextView
    private lateinit var totalCopySentenceQuestionsTextView: TextView
    private lateinit var questionsCorrectAnswersTextView: TextView
    private lateinit var totalGeneralQuestionsTextView: TextView
    private lateinit var mathCorrectAnswersTextView: TextView
    private lateinit var totalMathQuestionsTextView: TextView
    private lateinit var animalsCorrectAnswersTextView: TextView
    private lateinit var totalAnimalsQuestionsTextView: TextView
    private lateinit var comparisonCorrectAnswersTextView: TextView
    private lateinit var totalComparisonQuestionsTextView: TextView
    private lateinit var sentenceAgainCorrectAnswersTextView: TextView
    private lateinit var totalSentenceAgainQuestionsTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics_tym)

        basicCorrectAnswersTextView = findViewById(R.id.basicCorrectAnswersTextView)
        totalBasicQuestionsTextView = findViewById(R.id.totalBasicQuestionsTextView)
        copySentenceCorrectAnswersTextView = findViewById(R.id.copySentenceCorrectAnswersTextView)
        totalCopySentenceQuestionsTextView = findViewById(R.id.totalCopySentenceQuestionsTextView)
        questionsCorrectAnswersTextView = findViewById(R.id.questionsCorrectAnswersTextView)
        totalGeneralQuestionsTextView = findViewById(R.id.totalGeneralQuestionsTextView)
        mathCorrectAnswersTextView = findViewById(R.id.mathCorrectAnswersTextView)
        totalMathQuestionsTextView = findViewById(R.id.totalMathQuestionsTextView)
        animalsCorrectAnswersTextView = findViewById(R.id.animalsCorrectAnswersTextView)
        totalAnimalsQuestionsTextView = findViewById(R.id.totalAnimalsQuestionsTextView)
        comparisonCorrectAnswersTextView = findViewById(R.id.comparisonCorrectAnswersTextView)
        totalComparisonQuestionsTextView = findViewById(R.id.totalComparisonQuestionsTextView)
        sentenceAgainCorrectAnswersTextView = findViewById(R.id.sentenceAgainCorrectAnswersTextView)
        totalSentenceAgainQuestionsTextView = findViewById(R.id.totalSentenceAgainQuestionsTextView)

        // Get statistics from shared preferences
        val sharedPreferences = getSharedPreferences("CognitiveAssessmentApp", Context.MODE_PRIVATE)
        val basicCorrectAnswers = sharedPreferences.getInt("basicCorrectAnswers", 0)
        val totalBasicQuestions = sharedPreferences.getInt("totalBasicQuestions", 0)
        val copySentenceCorrectAnswers = sharedPreferences.getInt("copySentenceCorrectAnswers", 0)
        val totalCopySentenceQuestions = sharedPreferences.getInt("totalCopySentenceQuestions", 1)
        val questionsCorrectAnswers = sharedPreferences.getInt("questionsCorrectAnswers", 0)
        val totalGeneralQuestions = sharedPreferences.getInt("totalGeneralQuestions", 2)
        val mathCorrectAnswers = sharedPreferences.getInt("mathCorrectAnswers", 0)
        val totalMathQuestions = sharedPreferences.getInt("totalMathQuestions", 4)
        val animalsCorrectAnswers = sharedPreferences.getInt("animalsCorrectAnswers", 0)
        val totalAnimalsQuestions = sharedPreferences.getInt("totalAnimalsQuestions", 5)
        val comparisonCorrectAnswers = sharedPreferences.getInt("comparisonCorrectAnswers", 0)
        val totalComparisonQuestions = sharedPreferences.getInt("totalComparisonQuestions", 2)
        val sentenceAgainCorrectAnswers = sharedPreferences.getInt("sentenceAgainCorrectAnswers", 0)
        val totalSentenceAgainQuestions = sharedPreferences.getInt("totalSentenceAgainQuestions", 1)

        displayStatistics(
            basicCorrectAnswers, totalBasicQuestions, copySentenceCorrectAnswers, totalCopySentenceQuestions,
            questionsCorrectAnswers, totalGeneralQuestions, mathCorrectAnswers, totalMathQuestions,
            animalsCorrectAnswers, totalAnimalsQuestions, comparisonCorrectAnswers, totalComparisonQuestions,
            sentenceAgainCorrectAnswers, totalSentenceAgainQuestions
        )
    }

    private fun displayStatistics(
        basicCorrectAnswers: Int, totalBasicQuestions: Int, copySentenceCorrectAnswers: Int, totalCopySentenceQuestions: Int,
        questionsCorrectAnswers: Int, totalGeneralQuestions: Int, mathCorrectAnswers: Int, totalMathQuestions: Int,
        animalsCorrectAnswers: Int, totalAnimalsQuestions: Int, comparisonCorrectAnswers: Int, totalComparisonQuestions: Int,
        sentenceAgainCorrectAnswers: Int, totalSentenceAgainQuestions: Int
    ) {
        basicCorrectAnswersTextView.text = "Correct Answers for Basic Questions: $basicCorrectAnswers"
        totalBasicQuestionsTextView.text = "Total Basic Questions: $totalBasicQuestions"
        copySentenceCorrectAnswersTextView.text = "Correct Answers for Copy Sentence: $copySentenceCorrectAnswers"
        totalCopySentenceQuestionsTextView.text = "Total Copy Sentence Questions: $totalCopySentenceQuestions"
        questionsCorrectAnswersTextView.text = "Correct Answers for General Questions: $questionsCorrectAnswers"
        totalGeneralQuestionsTextView.text = "Total General Questions: $totalGeneralQuestions"
        mathCorrectAnswersTextView.text = "Correct Answers for Math: $mathCorrectAnswers"
        totalMathQuestionsTextView.text = "Total Math Questions: $totalMathQuestions"
        animalsCorrectAnswersTextView.text = "Correct Answers for Animals: $animalsCorrectAnswers"
        totalAnimalsQuestionsTextView.text = "Total Animal Questions: $totalAnimalsQuestions"
        comparisonCorrectAnswersTextView.text = "Correct Answers for Comparisons: $comparisonCorrectAnswers"
        totalComparisonQuestionsTextView.text = "Total Comparison Questions: $totalComparisonQuestions"
        sentenceAgainCorrectAnswersTextView.text = "Correct Answers for Sentence Again: $sentenceAgainCorrectAnswers"
        totalSentenceAgainQuestionsTextView.text = "Total Sentence Again Questions: $totalSentenceAgainQuestions"
    }
}
