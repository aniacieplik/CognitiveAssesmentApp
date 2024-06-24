package com.example.cognitiveassestmentapp.games.MIS

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.cognitiveassestmentapp.R

class MisActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis)

        val startButton = findViewById<Button>(R.id.start_mis_button)
        startButton.setOnClickListener {
            startActivity(Intent(this, FreeRecallActivity::class.java))
        }
    }
}
