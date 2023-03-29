package com.example.assignment3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : SnackBarActivity() {
    private lateinit var email: String
    private lateinit var password: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val signUp: TextView = findViewById(R.id.signUp)
        signUp.setOnClickListener {
            startSignUpActivity()
        }

        val forgetPassword: TextView = findViewById(R.id.forgetPassword)
        forgetPassword.setOnClickListener{
            startForgetPasswordActivity()
        }

        loginOnClickButton()
    }

    private fun loginOnClickButton(){

        val loginButton: View = findViewById(R.id.loginButton)

        loginButton.setOnClickListener {
            logInUser()
        }
    }

    private fun startSignUpActivity(){
        val intent = Intent (this, SignUpActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun startForgetPasswordActivity(){
        val intent = Intent (this, ForgetPasswordActivity::class.java)
        startActivity(intent)
    }

    private fun startMainActivity(){
        val intent = Intent (this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun validateLoginFields(): Boolean{
        email = findViewById<TextView>(R.id.email).text.toString()
        password = findViewById<TextView>(R.id.password).text.toString()

        return when{
            email.isEmpty() -> {
                showSnackBar(resources.getString(R.string.emailIsEmpty),true)
                false
            }
            password.isEmpty() -> {
                showSnackBar(resources.getString(R.string.passwordIsEmpty),true)
                false
            }
            else -> {
                //showSnackBar("Success",false)
                true
            }
        }
    }

    private fun logInUser(){

        showWaitDialog()

        if (validateLoginFields()){
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                .addOnCompleteListener {

                    hideWaitDialog()

                    if (it.isSuccessful) {
                        Toast.makeText(this,resources.getString(R.string.successLogIn),
                            Toast.LENGTH_LONG).show()
                        startMainActivity()
                    } else {
                        showSnackBar(it.exception!!.message.toString(),true)
                    }
                }
        }
    }

}