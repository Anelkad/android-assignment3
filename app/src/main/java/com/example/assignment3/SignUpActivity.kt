package com.example.assignment3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SignUpActivity : SnackBarActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signUpButton()
        startLoginActivity()
    }
    private fun signUpButton(){
        val loginButton: TextView = findViewById(R.id.signUpButton)
        loginButton.setOnClickListener {
            validateSignUpFields()
        }
    }
    private fun startLoginActivity(){
        val signUp: TextView = findViewById(R.id.login)
        signUp.setOnClickListener {
            val intent = Intent (this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun validateSignUpFields(): Boolean{
        val firstName: TextView = findViewById(R.id.firstName)
        val lastName: TextView = findViewById(R.id.lastName)
        val email: TextView = findViewById(R.id.email)
        val password: TextView = findViewById(R.id.password)
        val confirmPassword: TextView = findViewById(R.id.confirmPassword)

        return when{
            firstName.text.toString().isEmpty() -> {
                showSnackBar(resources.getString(R.string.firstNameIsEmpty),true)
                false
            }
            lastName.text.toString().isEmpty() -> {
                showSnackBar(resources.getString(R.string.lastNameIsEmpty),true)
                false
            }
            email.text.toString().isEmpty() -> {
                showSnackBar(resources.getString(R.string.emailIsEmpty),true)
                false
            }
            password.text.toString().isEmpty() -> {
                showSnackBar(resources.getString(R.string.passwordIsEmpty),true)
                false
            }
            confirmPassword.text.toString().isEmpty() -> {
                showSnackBar(resources.getString(R.string.confirmPasswordIsEmpty),true)
                false
            }
            else -> {
                showSnackBar("Success",false)
                true
            }
        }
    }
}