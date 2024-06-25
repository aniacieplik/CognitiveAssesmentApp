package com.example.cognitiveassestmentapp.games.BAS

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.cognitiveassestmentapp.R

/**
 * Activity for the "Spell World Backwards" game where users are required to spell the word "world" backwards.
 */
class SpellWorldBackwards : AppCompatActivity() {

    private lateinit var inputEditText: EditText
    private lateinit var submitButton: Button
    private val correctAnswer = "DLROW"

    /**
     * Called when the activity is first created. Initializes the UI elements and sets up
     * the submit button's click listener.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down
     *                           then this Bundle contains the data it most recently supplied. Note: Otherwise it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spell_world_backwards)

        inputEditText = findViewById(R.id.inputEditText)
        submitButton = findViewById(R.id.submitButton)

        submitButton.setOnClickListener {
            val userInput = inputEditText.text.toString().trim().uppercase()
            val spellPoints = userInput.indices.count { i -> i < correctAnswer.length && userInput[i] == correctAnswer[i] }

            val sharedPreferences = getSharedPreferences("CognitiveAssessmentApp", Context.MODE_PRIVATE)
            val totalSpellPoints = sharedPreferences.getInt("totalSpellPoints", 0) + spellPoints

            with(sharedPreferences.edit()) {
                putInt("totalSpellPoints", totalSpellPoints)
                apply()
            }

            val intent = Intent(this, RememberWordsInput::class.java)
            startActivity(intent)
        }
    }
}
