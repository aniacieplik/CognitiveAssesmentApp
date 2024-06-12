package com.example.cognitiveassestmentapp.registration

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import androidx.lifecycle.lifecycleScope
import com.example.cognitiveassestmentapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Date
import java.util.Calendar
import java.util.Locale

/**
 * An activity that handles the registration of new users by collecting user details and registering them using Firebase Authentication.
 * Upon successful registration, user details are stored in Firebase Firestore. The activity provides fields for entering user information
 * such as email, name, password, date of birth, and sex. It also handles input validation and displays appropriate error or success messages.
 *
 * Inherits from [BaseActivity] to utilize common UI functionalities like showing error or success notifications via Snackbar.
 */
class RegisterActivity : BaseActivity() {

    private var inputEmail: EditText? = null
    private var inputFirstName: EditText? = null
    private var inputLastName: EditText? = null
    private var inputPassword: EditText? = null
    private var inputRepeatPassword: EditText? = null
    private lateinit var inputDOB: EditText
    private lateinit var registerButton: Button

    /**
     * Sets up the activity layout and initializes UI components on activity creation.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the most recent data supplied in onSaveInstanceState(Bundle).
     *                           Otherwise, it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        registerButton = findViewById(R.id.registerButton)
        inputEmail = findViewById(R.id.inputEmail)
        inputFirstName = findViewById(R.id.inputFirstName)
        inputLastName = findViewById(R.id.inputLastName)
        inputPassword = findViewById(R.id.inputPassword)
        inputRepeatPassword = findViewById(R.id.inputRepeatPassword)
        inputDOB = findViewById(R.id.inputDOB)

        inputDOB.isFocusable = false
        inputDOB.setOnClickListener {
            showDatePickerDialog()
        }

        registerButton.setOnClickListener {
            registerUser()
        }
    }

    /**
     * Validates user input before registration.
     *
     * @return Boolean indicating whether the input is valid.
     */
    private fun validateRegisterDetails(): Boolean {
        return when {
            TextUtils.isEmpty(inputEmail?.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }

            TextUtils.isEmpty(inputFirstName?.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_first_name), true)
                false
            }

            TextUtils.isEmpty(inputLastName?.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_last_name), true)
                false
            }

            TextUtils.isEmpty(inputPassword?.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }

            TextUtils.isEmpty(inputRepeatPassword?.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_repeat_password), true)
                false
            }

            inputPassword?.text.toString()
                .trim { it <= ' ' } != inputRepeatPassword?.text.toString().trim { it <= ' ' } -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_password_mismatch), true)
                false
            }

            TextUtils.isEmpty(inputDOB?.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_dob), true)
                false
            }

            else -> true
        }
    }

    /**
     * Handles the user registration process using Firebase Authentication and stores additional details in Firestore.
     */
    private fun registerUser() {
        if (validateRegisterDetails()) {
            val email: String = inputEmail?.text.toString().trim()
            val firstName: String = inputFirstName?.text.toString().trim()
            val lastName: String = inputLastName?.text.toString().trim()
            val password: String = inputPassword?.text.toString().trim()
            val dateOfBirth: Date = Date.valueOf(inputDOB.text.toString().trim())

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val firebaseUser: FirebaseUser = task.result?.user!!
                        val userId = firebaseUser.uid

                        saveUserToFirestore(userId, firstName, lastName, dateOfBirth)
                        showErrorSnackBar(
                            "You are registered successfully. Your user id is $userId.",
                            false
                        )
                    } else {
                        showErrorSnackBar(task.exception!!.message.toString(), true)
                    }
                }
        }
    }

    /**
     * Saves user data to Firestore after successful authentication.
     *
     * @param userId Unique identifier for the user.
     * @param firstName User's first name.
     * @param lastName User's last name.
     * @param dateOfBirth User's date of birth.
     */
    private fun saveUserToFirestore(
        userId: String,
        firstName: String,
        lastName: String,
        dateOfBirth: Date
    ) {
        val db = FirebaseFirestore.getInstance()
        val user = hashMapOf(
            "userId" to userId,
            "firstName" to firstName,
            "lastName" to lastName,
            "dateOfBirth" to dateOfBirth
        )

        db.collection("users")
            .document(userId)
            .set(user)
            .addOnSuccessListener {
                startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
                finish()
            }
            .addOnFailureListener { e ->
                showErrorSnackBar("Error saving user: ${e.message}", true)
            }
    }

    fun goToLogin(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, day ->
                val selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, day)
                inputDOB.setText(selectedDate)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }
}
