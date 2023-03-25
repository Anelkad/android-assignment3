package com.example.assignment3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        startSignUpActivity()

    }

    private fun startSignUpActivity(){
        val signUp: TextView = findViewById(R.id.signUp)
        signUp.setOnClickListener {
            val intent = Intent (this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}