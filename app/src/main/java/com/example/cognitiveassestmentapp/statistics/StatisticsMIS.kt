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

/**
 * Activity for displaying and saving statistics of the MIS games.
 */
class StatisticsMIS : AppCompatActivity() {

    private lateinit var pointsTextView: TextView
    private lateinit var possiblePointsTextView: TextView
    private lateinit var returnButton: Button

    /**
     * Called when the activity is first created. Initializes the UI elements and sets up
     * the return button's click listener.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down
     *                           then this Bundle contains the data it most recently supplied. Note: Otherwise it is null.
     */
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

    /**
     * Saves the statistics of the MIS games to Firebase Firestore.
     *
     * @param points The points scored by the user.
     * @param totalPossiblePoints The total possible points in the game.
     */
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
