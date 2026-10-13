package com.sophia.movieapp

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RegistroActivity : AppCompatActivity() {

    lateinit var login_btn : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Date field
        val date_field = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.birthdate_field)
        date_field.setOnClickListener { showDatePicker() }

        // Sign up button
        val signUpBtn = findViewById<Button>(R.id.signin_btn)

        signUpBtn.setOnClickListener { validateSignUp() }

        login_btn = findViewById<TextView>(R.id.login_text)

        login_btn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }

    private fun showDatePicker(){
        val date_field = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.birthdate_field)
        val calendar = java.util.Calendar.getInstance()
        val year = calendar.get(java.util.Calendar.YEAR)
        val month = calendar.get(java.util.Calendar.MONTH)
        val day = calendar.get(java.util.Calendar.DAY_OF_MONTH)

        val datePicker = android.app.DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val date = String.format(
                    java.util.Locale.getDefault(),
                    "%02d/%02d/%04d",
                    selectedDay,
                    selectedMonth + 1,
                    selectedYear
                )
                date_field.setText(date)
            },
            year,
            month,
            day
        )
        datePicker.datePicker.maxDate = calendar.timeInMillis

        datePicker.show()
    }

    private fun validateSignUp(){
        // fields
        val name = findViewById<TextInputEditText>(R.id.name_field).text.toString().trim()

        val email = findViewById<TextInputEditText>(R.id.rEmail_field).text.toString().trim()

        val password = findViewById<TextInputEditText>(R.id.rPass_field).text.toString()

        val vPassword = findViewById<TextInputEditText>(R.id.rVPass_field).text.toString()

        val birthdate = findViewById<TextInputEditText>(R.id.birthdate_field).text.toString().trim()

        // layouts

        val nameLayout = findViewById<TextInputLayout>(R.id.name_layout)

        val emailLayout = findViewById<TextInputLayout>(R.id.email_layout)

        val passwordLayout = findViewById<TextInputLayout>(R.id.pass_layout)

        val vPasswordLayout = findViewById<TextInputLayout>(R.id.vPass_layout)

        val dateLayout = findViewById<TextInputLayout>(R.id.birthdate_layout)

        // clear
        nameLayout.error = null
        emailLayout.error = null
        passwordLayout.error = null
        vPasswordLayout.error = null
        dateLayout.error = null

        var valid = true

        // check name
        if (name.isBlank()) {
            nameLayout.error = "Enter your name"
            valid = false
        }

        // check email
        if (email.isBlank()) {
            emailLayout.error = "Enter your e-mail"
            valid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailLayout.error = "Enter a valid e-mail"
            valid = false
        }

        // check pass
        if (password.isBlank()) {
            passwordLayout.error = "Enter a password"
            valid = false
        }

        // check pass verification
        if (vPassword.isBlank()) {
            vPasswordLayout.error = "Verify your password"
            valid = false
        } else if (password != vPassword) {
            vPasswordLayout.error = "Passwords don't match"
            valid = false
        }

        // check age (18+)
        if(birthdate.isBlank()) {
            dateLayout.error = "Select your date of birht"
            valid = false
        } else {
            val date = convertDate(birthdate)

            if (date == null) {
                dateLayout.error = "Invalid date"
                valid = false
            } else if (!isOfAge(date)) {
                dateLayout.error = "You must be at least 18 years old"
                valid = false
            }
        }

        // result
        if (valid) {
            Toast.makeText(this, "Your signed up!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun convertDate(dateText: String) : Calendar? {
        return try {
            val format = SimpleDateFormat(
                "dd/MM/yyyy",
                Locale.getDefault()
            )
            format.isLenient = false

            val date = format.parse(dateText) ?: return null

            if (format.format(date) != dateText) {
                return null
            }

            Calendar.getInstance().apply {
                time = date
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
        } catch (e: Exception) {
            null
        }
    }

    private fun isOfAge(birthdate: Calendar): Boolean {
        val today = Calendar.getInstance()

        var age = today.get(Calendar.YEAR) - birthdate.get(Calendar.YEAR)

        val noBirthdayYet = today.get(Calendar.MONTH) < birthdate.get(Calendar.MONTH) ||(today.get(
            Calendar.MONTH) == birthdate.get(Calendar.MONTH) && today.get(Calendar.DAY_OF_MONTH) < birthdate.get(Calendar.DAY_OF_MONTH))

        if (noBirthdayYet) {
            age--
        }

        return age >= 18
    }
}