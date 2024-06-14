package com.example.cognitiveassestmentapp.games.TYM

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cognitiveassestmentapp.R
import java.util.Calendar

class Questions : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)

        val primeMinisterInput: EditText = findViewById(R.id.primeMinisterInput)
        val ww2YearInput: EditText = findViewById(R.id.ww2YearInput)
        val submitQuestionsButton: Button = findViewById(R.id.submitQuestionsButton)

        ww2YearInput.setOnClickListener {
            showYearPickerDialog(ww2YearInput)
        }

        submitQuestionsButton.setOnClickListener {
            val primeMinister = primeMinisterInput.text.toString()
            val ww2Year = ww2YearInput.text.toString()

            if (primeMinister == "Donald Tusk" && ww2Year == "1939") {
                Toast.makeText(this, "Correct! Well done!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MathQuestions::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Incorrect. Please try again.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showYearPickerDialog(editText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, _, _ ->
                editText.setText(selectedYear.toString())
            },
            year, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
        )

        // Disable day and month selection
        datePickerDialog.datePicker.findViewById<android.widget.DatePicker>(resources.getIdentifier("android:id/day", null, null))?.visibility = android.view.View.GONE
        datePickerDialog.datePicker.findViewById<android.widget.DatePicker>(resources.getIdentifier("android:id/month", null, null))?.visibility = android.view.View.GONE

        // Alternatively, you can force the date picker to display in calendar view mode to hide spinners
        datePickerDialog.datePicker.calendarViewShown = false
        datePickerDialog.datePicker.spinnersShown = true

        datePickerDialog.show()
    }
}
