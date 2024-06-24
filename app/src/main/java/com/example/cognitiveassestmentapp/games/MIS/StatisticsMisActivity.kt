package com.example.cognitiveassestmentapp.games.MIS

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cognitiveassestmentapp.R

class StatisticsMisActivity : AppCompatActivity() {

    private lateinit var pointsTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics_mis)

        pointsTextView = findViewById(R.id.points_textview)

        // Retrieve points from intent extras
        val points = intent.getIntExtra("points", 0)

        // Display points in TextView
        pointsTextView.text = "Points Earned: $points"
    }
}
