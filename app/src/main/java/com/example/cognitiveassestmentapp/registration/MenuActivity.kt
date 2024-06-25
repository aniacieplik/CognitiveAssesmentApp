package com.example.cognitiveassestmentapp.registration

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.cognitiveassestmentapp.R
import com.example.cognitiveassestmentapp.games.BAS.RememberWords
import com.example.cognitiveassestmentapp.games.MIS.MisActivity
import com.example.cognitiveassestmentapp.games.TYM.BasicQuestions
import com.example.cognitiveassestmentapp.statistics.UltimateStatistics

/**
 * Activity for the main menu where users can navigate to different tests and view statistics.
 */
class MenuActivity : AppCompatActivity(), View.OnClickListener {

    /**
     * Called when the activity is first created. Initializes the UI elements and sets up
     * click listeners for the menu options.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down
     *                           then this Bundle contains the data it most recently supplied. Note: Otherwise it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        findViewById<View>(R.id.tymtest).setOnClickListener(this)
        findViewById<View>(R.id.mistest).setOnClickListener(this)
        findViewById<View>(R.id.bastest).setOnClickListener(this)
        findViewById<View>(R.id.view_statistics).setOnClickListener(this)
    }

    /**
     * Handles the click events for the menu options and navigates to the corresponding activity.
     *
     * @param view The view that was clicked.
     */
    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.tymtest -> {
                startActivity(Intent(this, BasicQuestions::class.java))
            }
            R.id.bastest -> {
                startActivity(Intent(this, RememberWords::class.java))
            }
            R.id.mistest -> {
                startActivity(Intent(this, MisActivity::class.java))
            }
            R.id.view_statistics -> {
                startActivity(Intent(this, UltimateStatistics::class.java))
            }
        }
    }
}
