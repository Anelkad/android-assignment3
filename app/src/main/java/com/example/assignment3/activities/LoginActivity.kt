package com.example.assignment3.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.assignment3.R
import com.example.assignment3.firestore.FirestoreClass
import com.example.assignment3.models.User
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

    private fun startDashboardUserActivity(){
        val intent = Intent (this, DashboardUserActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun startDashboardAdminActivity(){
        val intent = Intent (this, DashboardAdminActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun validateLoginFields(): Boolean{
        email = findViewById<TextView>(R.id.email).text.toString()
        password = findViewById<TextView>(R.id.password).text.toString()

        return when{
            email.isEmpty() -> {
                hideWaitDialog()
                showSnackBar(resources.getString(R.string.emailIsEmpty),true)
                false
            }
            password.isEmpty() -> {
                hideWaitDialog()
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
                        FirestoreClass().getUserDetails(this@LoginActivity)
                        Toast.makeText(this,resources.getString(R.string.successLogIn),
                            Toast.LENGTH_LONG).show()
                    } else {
                        showSnackBar(it.exception!!.message.toString(),true)
                    }
                }
        }
    }

    fun userLoggedInSuccess(user: User){
        Log.i("First name: ", user.firstName)
        Log.i("Last name: ", user.lastName)
        Log.i("Email: ", user.email)
        Log.i("Role: ", user.role)

        if (user.role=="admin"){
            startDashboardAdminActivity()
        }else if (user.role=="client"){
            startDashboardUserActivity()
        }
    }

}