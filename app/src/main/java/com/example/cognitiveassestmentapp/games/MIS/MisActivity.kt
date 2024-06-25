package com.example.cognitiveassestmentapp.games.MIS

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.cognitiveassestmentapp.R

/**
 * Activity for the "MIS" game which serves as an entry point to start the MIS-related games.
 */
class MisActivity : AppCompatActivity() {

    /**
     * Called when the activity is first created. Initializes the UI elements and sets up
     * the start button's click listener.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down
     *                           then this Bundle contains the data it most recently supplied. Note: Otherwise it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis)

        val startButton = findViewById<Button>(R.id.start_mis_button)
        startButton.setOnClickListener {
            startActivity(Intent(this, ItemActivity::class.java))
        }
    }
}
