package com.example.assignment3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.TextView

class LoginActivity : SnackBarActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        startSignUpActivity()
        loginButton()
    }

    private fun loginButton(){
        val loginButton: TextView = findViewById(R.id.loginButton)
        loginButton.setOnClickListener {
            validateLoginFields()
        }
    }

    private fun startSignUpActivity(){
        val signUp: TextView = findViewById(R.id.signUp)
        signUp.setOnClickListener {
            val intent = Intent (this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun validateLoginFields(): Boolean{
        val email: TextView = findViewById(R.id.email)
        val password: TextView = findViewById(R.id.password)

        return when{
            email.text.toString().isEmpty() -> {
                showSnackBar(resources.getString(R.string.emailIsEmpty),true)
                false
            }
            password.text.toString().isEmpty() -> {
                showSnackBar(resources.getString(R.string.passwordIsEmpty),true)
                false
            }
            else -> {
                showSnackBar("Success",false)
                true
            }
        }
    }

}