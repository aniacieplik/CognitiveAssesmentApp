package com.example.cognitiveassestmentapp.statistics

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cognitiveassestmentapp.R
import com.example.cognitiveassestmentapp.registration.MenuActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Activity for displaying and saving statistics of the TYM games.
 */
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
    private lateinit var returnButton: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    /**
     * Called when the activity is first created. Initializes the UI elements and sets up
     * the return button's click listener.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down
     *                           then this Bundle contains the data it most recently supplied. Note: Otherwise it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics_tym)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

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
        returnButton = findViewById(R.id.returnButton)

        returnButton.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

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

        saveStatisticsToFirebase(
            basicCorrectAnswers, totalBasicQuestions, copySentenceCorrectAnswers, totalCopySentenceQuestions,
            questionsCorrectAnswers, totalGeneralQuestions, mathCorrectAnswers, totalMathQuestions,
            animalsCorrectAnswers, totalAnimalsQuestions, comparisonCorrectAnswers, totalComparisonQuestions,
            sentenceAgainCorrectAnswers, totalSentenceAgainQuestions
        )

        displayStatistics(
            basicCorrectAnswers, totalBasicQuestions, copySentenceCorrectAnswers, totalCopySentenceQuestions,
            questionsCorrectAnswers, totalGeneralQuestions, mathCorrectAnswers, totalMathQuestions,
            animalsCorrectAnswers, totalAnimalsQuestions, comparisonCorrectAnswers, totalComparisonQuestions,
            sentenceAgainCorrectAnswers, totalSentenceAgainQuestions
        )
    }

    /**
     * Saves the statistics of the TYM games to Firebase Firestore.
     *
     * @param basicCorrectAnswers The number of correct answers for basic questions.
     * @param totalBasicQuestions The total number of basic questions.
     * @param copySentenceCorrectAnswers The number of correct answers for copy sentence.
     * @param totalCopySentenceQuestions The total number of copy sentence questions.
     * @param questionsCorrectAnswers The number of correct answers for general questions.
     * @param totalGeneralQuestions The total number of general questions.
     * @param mathCorrectAnswers The number of correct answers for math questions.
     * @param totalMathQuestions The total number of math questions.
     * @param animalsCorrectAnswers The number of correct answers for animal questions.
     * @param totalAnimalsQuestions The total number of animal questions.
     * @param comparisonCorrectAnswers The number of correct answers for comparison questions.
     * @param totalComparisonQuestions The total number of comparison questions.
     * @param sentenceAgainCorrectAnswers The number of correct answers for sentence again.
     * @param totalSentenceAgainQuestions The total number of sentence again questions.
     */
    private fun saveStatisticsToFirebase(
        basicCorrectAnswers: Int, totalBasicQuestions: Int, copySentenceCorrectAnswers: Int, totalCopySentenceQuestions: Int,
        questionsCorrectAnswers: Int, totalGeneralQuestions: Int, mathCorrectAnswers: Int, totalMathQuestions: Int,
        animalsCorrectAnswers: Int, totalAnimalsQuestions: Int, comparisonCorrectAnswers: Int, totalComparisonQuestions: Int,
        sentenceAgainCorrectAnswers: Int, totalSentenceAgainQuestions: Int
    ) {
        val userId = auth.currentUser?.uid ?: return
        val userStatisticsRef = firestore.collection("users").document(userId)
            .collection("statistics").document("StatisticsTYM")
            .collection("attempts")

        userStatisticsRef.get().addOnSuccessListener { result ->
            val currentAttempt = result.size() + 1
            val attemptId = "attempt$currentAttempt"

            val statistics = mapOf(
                "basicCorrectAnswers" to basicCorrectAnswers,
                "copySentenceCorrectAnswers" to copySentenceCorrectAnswers,
                "questionsCorrectAnswers" to questionsCorrectAnswers,
                "mathCorrectAnswers" to mathCorrectAnswers,
                "animalsCorrectAnswers" to animalsCorrectAnswers,
                "comparisonCorrectAnswers" to comparisonCorrectAnswers,
                "sentenceAgainCorrectAnswers" to sentenceAgainCorrectAnswers,
            )

            userStatisticsRef.document(attemptId).set(statistics)
                .addOnSuccessListener {
                    Toast.makeText(this, "Statistics saved successfully.", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to save statistics: ${e.message}", Toast.LENGTH_LONG).show()
                }
        }.addOnFailureListener { e ->
            Toast.makeText(this, "Failed to retrieve attempts: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Displays the statistics of the TYM games.
     *
     * @param basicCorrectAnswers The number of correct answers for basic questions.
     * @param totalBasicQuestions The total number of basic questions.
     * @param copySentenceCorrectAnswers The number of correct answers for copy sentence.
     * @param totalCopySentenceQuestions The total number of copy sentence questions.
     * @param questionsCorrectAnswers The number of correct answers for general questions.
     * @param totalGeneralQuestions The total number of general questions.
     * @param mathCorrectAnswers The number of correct answers for math questions.
     * @param totalMathQuestions The total number of math questions.
     * @param animalsCorrectAnswers The number of correct answers for animal questions.
     * @param totalAnimalsQuestions The total number of animal questions.
     * @param comparisonCorrectAnswers The number of correct answers for comparison questions.
     * @param totalComparisonQuestions The total number of comparison questions.
     * @param sentenceAgainCorrectAnswers The number of correct answers for sentence again.
     * @param totalSentenceAgainQuestions The total number of sentence again questions.
     */
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
