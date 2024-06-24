package com.example.cognitiveassestmentapp.games.BAS

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cognitiveassestmentapp.R

class StatisticsBAS : AppCompatActivity() {

    private lateinit var pointsTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics_bas)

        pointsTextView = findViewById(R.id.pointsTextView)

        val points = intent.getIntExtra("POINTS", 0)
        pointsTextView.text = "Points: $points"
    }
}
