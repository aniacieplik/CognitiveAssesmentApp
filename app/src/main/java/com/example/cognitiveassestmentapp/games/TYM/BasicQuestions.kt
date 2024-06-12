package com.example.cognitiveassestmentapp.games.TYM

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cognitiveassestmentapp.R

class BasicQuestions : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_questions)

        val nameInput: EditText = findViewById(R.id.nameInput)
        val dayOfWeekInput: EditText = findViewById(R.id.dayOfWeekInput)
        val dateInput: EditText = findViewById(R.id.dateInput)
        val ageInput: EditText = findViewById(R.id.ageInput)
        val birthdayInput: EditText = findViewById(R.id.birthdayInput)
        val submitButton: Button = findViewById(R.id.submitButton)

        submitButton.setOnClickListener {
            val name = nameInput.text.toString()
            val dayOfWeek = dayOfWeekInput.text.toString()
            val date = dateInput.text.toString()
            val age = ageInput.text.toString()
            val birthday = birthdayInput.text.toString()

            if (name.isNotEmpty() && dayOfWeek.isNotEmpty() && date.isNotEmpty() && age.isNotEmpty() && birthday.isNotEmpty()) {
                Toast.makeText(this, "Questions answered. Moving to the next part.", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, CopySentence::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please answer all questions.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
