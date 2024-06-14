package com.example.cognitiveassestmentapp.games.TYM

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.cognitiveassestmentapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.Calendar

class BasicQuestions : AppCompatActivity() {

    private lateinit var nameInput: EditText
    private lateinit var dayOfWeekInput: EditText
    private lateinit var dateInput: EditText
    private lateinit var ageInput: EditText
    private lateinit var birthdayInput: EditText
    private lateinit var submitButton: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_questions)

        nameInput = findViewById(R.id.nameInput)
        dayOfWeekInput = findViewById(R.id.dayOfWeekInput)
        dateInput = findViewById(R.id.dateInput)
        ageInput = findViewById(R.id.ageInput)
        birthdayInput = findViewById(R.id.birthdayInput)
        submitButton = findViewById(R.id.submitButton)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        fetchUserName()

        dateInput.setOnClickListener {
            showDatePickerDialog(dateInput, true)
        }

        birthdayInput.setOnClickListener {
            showDatePickerDialog(birthdayInput, false)
        }

        submitButton.setOnClickListener {
            checkAnswers()
        }
    }

    private fun fetchUserName() {
        val userId = auth.currentUser?.uid
        userId?.let {
            database.child("users").child(it).child("name").get().addOnSuccessListener {
                nameInput.setText(it.value.toString())
            }
        }
    }

    private fun showDatePickerDialog(editText: EditText, isTodayDate: Boolean) {
        val calendar = Calendar.getInstance()
        val year = if (isTodayDate) 0 else calendar.get(Calendar.YEAR)
        val month = if (isTodayDate) 0 else calendar.get(Calendar.MONTH)
        val day = if (isTodayDate) 0 else calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
                editText.setText(selectedDate)
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    private fun checkAnswers() {
        var correctAnswers = 0

        // Check day of the week
        val currentDayOfWeek = DateFormat.format("EEEE", Calendar.getInstance()).toString()
        if (dayOfWeekInput.text.toString().equals(currentDayOfWeek, ignoreCase = true)) {
            correctAnswers++
        }

        // Check date
        val currentDate = DateFormat.format("yyyy-MM-dd", Calendar.getInstance()).toString()
        if (dateInput.text.toString() == currentDate) {
            correctAnswers++
        }

        // Check age
        val birthdate = birthdayInput.text.toString()
        val age = calculateAge(birthdate)
        if (ageInput.text.toString().toIntOrNull() == age) {
            correctAnswers++
        }

        // Check birthdate
        val registrationBirthdate = "2000-01-01" // Fetch from user's registration info
        if (birthdate == registrationBirthdate) {
            correctAnswers++
        }

        Log.d("BasicQuestions", "Correct Answers: $correctAnswers")

        // Save statistics
        saveStatistics(correctAnswers, 5)

        // Proceed to the next activity
        Log.d("BasicQuestions", "Redirecting to CopySentence Activity")
        val intent = Intent(this, CopySentence::class.java)
        startActivity(intent)
    }

    private fun calculateAge(birthdate: String): Int {
        val parts = birthdate.split("-")
        val birthYear = parts[0].toInt()
        val birthMonth = parts[1].toInt()
        val birthDay = parts[2].toInt()

        val today = Calendar.getInstance()
        var age = today.get(Calendar.YEAR) - birthYear

        if (today.get(Calendar.MONTH) < birthMonth || (today.get(Calendar.MONTH) == birthMonth && today.get(Calendar.DAY_OF_MONTH) < birthDay)) {
            age--
        }

        return age
    }

    private fun saveStatistics(correctAnswers: Int, totalQuestions: Int) {
        val sharedPreferences = getSharedPreferences("CognitiveAssessmentApp", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("basicCorrectAnswers", correctAnswers)
        editor.putInt("totalBasicQuestions", totalQuestions)
        editor.apply()
    }
}
