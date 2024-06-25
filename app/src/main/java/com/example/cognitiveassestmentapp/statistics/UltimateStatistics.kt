package com.example.cognitiveassestmentapp.statistics

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import com.example.cognitiveassestmentapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.math.roundToInt

class UltimateStatistics : AppCompatActivity() {

    private lateinit var scrollView: NestedScrollView
    private lateinit var spellPointsTextView: TextView
    private lateinit var rememberPointsTextView: TextView
    private lateinit var animalPointsTextView: TextView
    private lateinit var meanSpellPointsTextView: TextView
    private lateinit var meanRememberPointsTextView: TextView
    private lateinit var meanAnimalPointsTextView: TextView

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ultimate_statistics)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        scrollView = findViewById(R.id.scrollView)
        spellPointsTextView = findViewById(R.id.spellPointsTextView)
        rememberPointsTextView = findViewById(R.id.rememberPointsTextView)
        animalPointsTextView = findViewById(R.id.animalPointsTextView)
        meanSpellPointsTextView = findViewById(R.id.meanSpellPointsTextView)
        meanRememberPointsTextView = findViewById(R.id.meanRememberPointsTextView)
        meanAnimalPointsTextView = findViewById(R.id.meanAnimalPointsTextView)

        fetchStatistics()
    }

    private fun fetchStatistics() {
        val userId = auth.currentUser?.uid ?: return
        val userStatisticsRef = firestore.collection("users").document(userId)
            .collection("statistics")

        userStatisticsRef.get().addOnSuccessListener { result ->
            var totalSpellPoints = 0
            var totalRememberPoints = 0
            var totalAnimalPoints = 0
            var spellAttempts = 0
            var rememberAttempts = 0
            var animalAttempts = 0

            for (document in result.documents) {
                val attemptsRef = document.reference.collection("attempts")
                attemptsRef.get().addOnSuccessListener { attempts ->
                    for (attempt in attempts.documents) {
                        totalSpellPoints += attempt.getLong("spellPoints")?.toInt() ?: 0
                        totalRememberPoints += attempt.getLong("rememberPoints")?.toInt() ?: 0
                        totalAnimalPoints += attempt.getLong("animalPoints")?.toInt() ?: 0
                        spellAttempts++
                        rememberAttempts++
                        animalAttempts++
                    }
                    displayStatistics(totalSpellPoints, totalRememberPoints, totalAnimalPoints,
                        spellAttempts, rememberAttempts, animalAttempts)
                }
            }
        }.addOnFailureListener { e ->
            Toast.makeText(this, "Failed to retrieve statistics: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun displayStatistics(totalSpellPoints: Int, totalRememberPoints: Int, totalAnimalPoints: Int,
                                  spellAttempts: Int, rememberAttempts: Int, animalAttempts: Int) {
        spellPointsTextView.text = "Total Spell Points: $totalSpellPoints"
        rememberPointsTextView.text = "Total Remember Points: $totalRememberPoints"
        animalPointsTextView.text = "Total Animal Points: $totalAnimalPoints"

        meanSpellPointsTextView.text = "Mean Spell Points: ${calculateMean(totalSpellPoints, spellAttempts)}"
        meanRememberPointsTextView.text = "Mean Remember Points: ${calculateMean(totalRememberPoints, rememberAttempts)}"
        meanAnimalPointsTextView.text = "Mean Animal Points: ${calculateMean(totalAnimalPoints, animalAttempts)}"
    }

    private fun calculateMean(totalPoints: Int, attempts: Int): Float {
        return if (attempts > 0) {
            (totalPoints.toFloat() / attempts).roundToInt().toFloat()
        } else {
            0f
        }
    }
}
