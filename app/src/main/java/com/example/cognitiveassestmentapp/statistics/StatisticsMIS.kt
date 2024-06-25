package com.example.cognitiveassestmentapp.statistics

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
import java.util.*

class StatisticsMIS : AppCompatActivity() {

    private lateinit var pointsTextView: TextView
    private lateinit var possiblePointsTextView: TextView
    private lateinit var returnButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics_mis)

        pointsTextView = findViewById(R.id.points_textview)
        possiblePointsTextView = findViewById(R.id.possible_points_textview)
        returnButton = findViewById(R.id.return_button)

        val points = intent.getIntExtra("points", 0)
        val totalPossiblePoints = intent.getIntExtra("totalPossiblePoints", 0)

        pointsTextView.text = "Points Earned: ${8 - points}"
        possiblePointsTextView.text = "Total Possible Points: $totalPossiblePoints"

        returnButton.setOnClickListener {
            saveMISStatisticsToFirebase(points, totalPossiblePoints)
        }
    }

    private fun saveMISStatisticsToFirebase(points: Int, totalPossiblePoints: Int) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val db = FirebaseFirestore.getInstance()

        val adjustedPoints = 8 - points

        val userStatisticsRef = db.collection("users")
            .document(userId)
            .collection("statistics")
            .document("MISstatistics")
            .collection("attempts")

        userStatisticsRef.get().addOnSuccessListener { result ->
            val currentAttempt = result.size() + 1
            val attemptId = "attempt$currentAttempt"

            val statistics = hashMapOf(
                "timestamp" to Calendar.getInstance().time,
                "adjustedPoints" to adjustedPoints,
                "totalPossiblePoints" to totalPossiblePoints
            )

            userStatisticsRef.document(attemptId).set(statistics)
                .addOnSuccessListener {
                    startActivity(Intent(this, MenuActivity::class.java))
                    finish()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(
                        this,
                        "Failed to save attempt. Please try again later.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

        }
    }
}
