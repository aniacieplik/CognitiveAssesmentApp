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

/**
 * Activity for the "Basic Questions" game where users answer basic personal and temporal questions.
 */
class BasicQuestions : AppCompatActivity() {

    private lateinit var nameInput: EditText
    private lateinit var dayOfWeekInput: EditText
    private lateinit var dateInput: EditText
    private lateinit var ageInput: EditText
    private lateinit var birthdayInput: EditText
    private lateinit var submitButton: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    /**
     * Called when the activity is first created. Initializes the UI elements and sets up
     * the Firebase authentication and database references.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down
     *                           then this Bundle contains the data it most recently supplied. Note: Otherwise it is null.
     */
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

    /**
     * Fetches the user's name from the Firebase database and sets it in the name input field.
     */
    private fun fetchUserName() {
        val userId = auth.currentUser?.uid
        userId?.let {
            database.child("users").child(it).child("firstName").get().addOnSuccessListener {
                nameInput.setText(it.value.toString())
            }
        }
    }

    /**
     * Displays a date picker dialog for the user to select a date.
     *
     * @param editText The EditText field to set the selected date.
     * @param isTodayDate Boolean indicating if the selected date is today's date.
     */
    private fun showDatePickerDialog(editText: EditText, isTodayDate: Boolean) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

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

    /**
     * Checks the user's answers against the correct answers and calculates the score.
     */
    private fun checkAnswers() {
        var correctAnswers = 0

        val currentDayOfWeek = DateFormat.format("EEEE", Calendar.getInstance()).toString()
        val dayOfWeekInputText = dayOfWeekInput.text.toString().trim()
        Log.d("BasicQuestions", "Expected Day: $currentDayOfWeek, Input Day: $dayOfWeekInputText")
        if (dayOfWeekInputText.equals(currentDayOfWeek, ignoreCase = true)) {
            correctAnswers++
        }

        val currentDate = DateFormat.format("yyyy-MM-dd", Calendar.getInstance()).toString()
        val dateInputText = dateInput.text.toString().trim()
        Log.d("BasicQuestions", "Expected Date: $currentDate, Input Date: $dateInputText")
        if (dateInputText == currentDate) {
            correctAnswers++
        }

        val birthdate = birthdayInput.text.toString().trim()
        val ageInputText = ageInput.text.toString().toIntOrNull()
        if (ageInputText != null) {
            val age = calculateAge(birthdate)
            Log.d("BasicQuestions", "Expected Age: $age, Input Age: $ageInputText")
            if (ageInputText == age) {
                correctAnswers++
            }
        } else {
            Log.d("BasicQuestions", "Invalid age input: $ageInputText")
        }

        val registrationBirthdate = "2000-01-01"
        Log.d("BasicQuestions", "Expected Birthdate: $registrationBirthdate, Input Birthdate: $birthdate")
        if (birthdate == registrationBirthdate) {
            correctAnswers++
        }

        Log.d("BasicQuestions", "Correct Answers: $correctAnswers")

        saveStatistics(correctAnswers, 5)

        Log.d("BasicQuestions", "Redirecting to CopySentence Activity")
        val intent = Intent(this, CopySentence::class.java)
        startActivity(intent)
    }

    /**
     * Calculates the user's age based on the birthdate.
     *
     * @param birthdate The user's birthdate in "yyyy-MM-dd" format.
     * @return The calculated age.
     */
    private fun calculateAge(birthdate: String): Int {
        val parts = birthdate.split("-")
        if (parts.size != 3) {
            Log.d("BasicQuestions", "Invalid birthdate format: $birthdate")
            return -1
        }
        val birthYear = parts[0].toInt()
        val birthMonth = parts[1].toInt()
        val birthDay = parts[2].toInt()

        val today = Calendar.getInstance()
        var age = today.get(Calendar.YEAR) - birthYear

        if (today.get(Calendar.MONTH) < birthMonth - 1 || (today.get(Calendar.MONTH) == birthMonth - 1 && today.get(Calendar.DAY_OF_MONTH) < birthDay)) {
            age--
        }

        return age
    }

    /**
     * Saves the statistics of the "Basic Questions" game to shared preferences.
     *
     * @param correctAnswers The number of correct answers provided by the user.
     * @param totalQuestions The total number of questions asked in the game.
     */
    private fun saveStatistics(correctAnswers: Int, totalQuestions: Int) {
        val sharedPreferences = getSharedPreferences("CognitiveAssessmentApp", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("basicCorrectAnswers", correctAnswers)
        editor.putInt("totalBasicQuestions", totalQuestions)
        editor.apply()
    }
}
