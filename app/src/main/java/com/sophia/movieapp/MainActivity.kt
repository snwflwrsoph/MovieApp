package com.sophia.movieapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var login_btn : Button
    lateinit var signin_btn : Button
    lateinit var email_field: EditText
    lateinit var password_field: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        login_btn = findViewById<Button>(R.id.login_btn)
        signin_btn = findViewById<Button>(R.id.signin_btn)
        email_field = findViewById<EditText>(R.id.email_field)
        password_field = findViewById<EditText>(R.id.password_field)

        signin_btn.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }

        login_btn.setOnClickListener {
            var user = email_field.text.toString()
            var pass = password_field.text.toString()

            if(user == "admin" && pass == "777"){
                Toast.makeText(this, "You're in", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Incorrect credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }
}