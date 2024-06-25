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

class MenuActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        findViewById<View>(R.id.tymtest).setOnClickListener(this)
        findViewById<View>(R.id.mistest).setOnClickListener(this)
        findViewById<View>(R.id.bastest).setOnClickListener(this)
        findViewById<View>(R.id.view_statistics).setOnClickListener(this)
    }

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
