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
 * Activity for displaying and saving statistics of the BAS games.
 */
class StatisticsBAS : AppCompatActivity() {

    private lateinit var spellPointsTextView: TextView
    private lateinit var totalSpellPointsTextView: TextView
    private lateinit var rememberPointsTextView: TextView
    private lateinit var totalRememberPointsTextView: TextView
    private lateinit var animalPointsTextView: TextView
    private lateinit var totalAnimalPointsTextView: TextView
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
        setContentView(R.layout.activity_statistics_bas)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        spellPointsTextView = findViewById(R.id.spellPointsTextView)
        totalSpellPointsTextView = findViewById(R.id.totalSpellPointsTextView)
        rememberPointsTextView = findViewById(R.id.rememberPointsTextView)
        totalRememberPointsTextView = findViewById(R.id.totalRememberPointsTextView)
        animalPointsTextView = findViewById(R.id.animalPointsTextView)
        returnButton = findViewById(R.id.returnButton)

        returnButton.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

        val sharedPreferences = getSharedPreferences("CognitiveAssessmentApp", Context.MODE_PRIVATE)
        val totalSpellPoints = sharedPreferences.getInt("totalSpellPoints", 0)
        val totalRememberPoints = sharedPreferences.getInt("totalRememberPoints", 0)
        val totalAnimalPoints = sharedPreferences.getInt("totalAnimalPoints", 0)

        displayStatistics(totalSpellPoints, totalRememberPoints, totalAnimalPoints)
    }

    /**
     * Saves the statistics of the BAS games to Firebase Firestore.
     *
     * @param spellPoints The points scored in the "Spell Words Backwards" game.
     * @param totalSpellPoints The total points scored in the "Spell Words Backwards" game.
     * @param rememberPoints The points scored in the "Remember Words" game.
     * @param totalRememberPoints The total points scored in the "Remember Words" game.
     * @param animalPoints The points scored in the "Animal" game.
     * @param totalAnimalPoints The total points scored in the "Animal" game.
     */
    private fun saveStatisticsToFirebase(
        spellPoints: Int, totalSpellPoints: Int, rememberPoints: Int, totalRememberPoints: Int, animalPoints: Int, totalAnimalPoints: Int
    ) {
        val userId = auth.currentUser?.uid ?: return
        val userStatisticsRef = firestore.collection("users").document(userId)
            .collection("statistics").document("StatisticsBAS")
            .collection("attempts")

        userStatisticsRef.get().addOnSuccessListener { result ->
            val currentAttempt = result.size() + 1
            val attemptId = "attempt$currentAttempt"

            val statistics = mapOf(
                "spellPoints" to spellPoints,
                "rememberPoints" to rememberPoints,
                "animalPoints" to animalPoints,
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
     * Displays the statistics of the BAS games.
     *
     * @param totalSpellPoints The total points scored in the "Spell Words Backwards" game.
     * @param totalRememberPoints The total points scored in the "Remember Words" game.
     * @param totalAnimalPoints The total points scored in the "Animal" game.
     */
    private fun displayStatistics(totalSpellPoints: Int, totalRememberPoints: Int, totalAnimalPoints: Int) {
        spellPointsTextView.text = "Spell Words Backwards Points: $totalSpellPoints"
        rememberPointsTextView.text = "Remember Words Points: $totalRememberPoints"
        animalPointsTextView.text = "Animal Points: $totalAnimalPoints"

        totalSpellPointsTextView.text = "Total Spell Words Backwards Points: $totalSpellPoints"
        totalRememberPointsTextView.text = "Total Remember Words Points: $totalRememberPoints"
        totalAnimalPointsTextView.text = "Total Animal Points: $totalAnimalPoints"

        saveStatisticsToFirebase(totalSpellPoints, totalSpellPoints, totalRememberPoints, totalRememberPoints, totalAnimalPoints, totalAnimalPoints)
    }
}
