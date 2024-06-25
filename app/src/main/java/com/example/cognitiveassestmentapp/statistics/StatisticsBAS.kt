package com.example.cognitiveassestmentapp.statistics

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cognitiveassestmentapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class StatisticsBAS : AppCompatActivity() {

    private lateinit var spellPointsTextView: TextView
    private lateinit var totalSpellPointsTextView: TextView
    private lateinit var rememberPointsTextView: TextView
    private lateinit var totalRememberPointsTextView: TextView
    private lateinit var animalPointsTextView: TextView
    private lateinit var totalAnimalPointsTextView: TextView

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

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
        totalAnimalPointsTextView = findViewById(R.id.totalAnimalPointsTextView)

        val sharedPreferences = getSharedPreferences("CognitiveAssessmentApp", Context.MODE_PRIVATE)
        val spellPoints = intent.getIntExtra("SPELL_POINTS", 0)
        val rememberPoints = intent.getIntExtra("REMEMBER_POINTS", 0)
        val animalPoints = intent.getIntExtra("ANIMAL_POINTS", 0)

        val totalSpellPoints = sharedPreferences.getInt("totalSpellPoints", 0) + spellPoints
        val totalRememberPoints = sharedPreferences.getInt("totalRememberPoints", 0) + rememberPoints
        val totalAnimalPoints = sharedPreferences.getInt("totalAnimalPoints", 0) + animalPoints

        with(sharedPreferences.edit()) {
            putInt("totalSpellPoints", totalSpellPoints)
            putInt("totalRememberPoints", totalRememberPoints)
            putInt("totalAnimalPoints", totalAnimalPoints)
            apply()
        }

        saveStatisticsToFirebase(spellPoints, totalSpellPoints, rememberPoints, totalRememberPoints, animalPoints, totalAnimalPoints)

        displayStatistics(spellPoints, totalSpellPoints, rememberPoints, totalRememberPoints, animalPoints, totalAnimalPoints)
    }

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
                    // Handle success
                }
                .addOnFailureListener { e ->
                    // Handle failure
                }
        }
    }

    private fun displayStatistics(
        spellPoints: Int, totalSpellPoints: Int, rememberPoints: Int, totalRememberPoints: Int, animalPoints: Int, totalAnimalPoints: Int
    ) {
        spellPointsTextView.text = "Spell Words Backwards Points: $spellPoints"
        totalSpellPointsTextView.text = "Total Spell Words Backwards Points: $totalSpellPoints"
        rememberPointsTextView.text = "Remember Words Points: $rememberPoints"
        totalRememberPointsTextView.text = "Total Remember Words Points: $totalRememberPoints"
        animalPointsTextView.text = "Animal Points: $animalPoints"
        totalAnimalPointsTextView.text = "Total Animal Points: $totalAnimalPoints"
    }
}
