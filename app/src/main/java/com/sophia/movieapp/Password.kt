package com.sophia.movieapp

import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class Password : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_password)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun validate(){
        val email = findViewById<TextInputEditText>(R.id.recoverEmail_field).text.toString().trim()

        val emailLayout = findViewById<TextInputLayout>(R.id.recoverEmail_layout)

        emailLayout.error = null

        var valid = true

        if (email.isBlank()) {
            emailLayout.error = "Enter your e-mail"
            valid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailLayout.error = "Enter a valid e-mail"
            valid = false
        }

        if (valid) {
            Toast.makeText(this, "The recovery link was sent!", Toast.LENGTH_SHORT).show()
        }
    }
}