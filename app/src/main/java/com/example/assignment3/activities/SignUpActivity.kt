package com.example.assignment3.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.assignment3.R
import com.example.assignment3.firestore.FirestoreClass
import com.example.assignment3.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignUpActivity : SnackBarActivity() {
    private lateinit var firstName: String
    private lateinit var lastName: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var confirmPassword: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signUpOnClickButton()

        val signUp: TextView = findViewById(R.id.login)
        signUp.setOnClickListener {
            startLoginActivity()
        }
    }
    private fun signUpOnClickButton(){
        val loginButton: View = findViewById(R.id.signUpButton)
        loginButton.setOnClickListener {
            signUpUser()
        }
    }
    private fun startLoginActivity(){
        val intent = Intent (this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun validateSignUpFields(): Boolean{
        firstName = findViewById<TextView>(R.id.firstName).text.toString()
        lastName = findViewById<TextView>(R.id.lastName).text.toString()
        email = findViewById<TextView>(R.id.email).text.toString()
        password = findViewById<TextView>(R.id.password).text.toString()
        confirmPassword = findViewById<TextView>(R.id.confirmPassword).text.toString()

        return when{
            firstName.isEmpty() -> {
                hideWaitDialog()
                showSnackBar(resources.getString(R.string.firstNameIsEmpty),true)
                false
            }
            lastName.isEmpty() -> {
                hideWaitDialog()
                showSnackBar(resources.getString(R.string.lastNameIsEmpty),true)
                false
            }
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
            confirmPassword.isEmpty() -> {
                hideWaitDialog()
                showSnackBar(resources.getString(R.string.confirmPasswordIsEmpty),true)
                false
            }
            confirmPassword!= password -> {
                hideWaitDialog()
                showSnackBar(resources.getString(R.string.passwordNotEqual),true)
                false
            }
            else -> {
                //showSnackBar("Success",false)
                true
            }
        }
    }

    private fun signUpUser(){

        showWaitDialog()

        if (validateSignUpFields()){
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {

                    hideWaitDialog()

                    if (it.isSuccessful) {
                        val firebaseUser: FirebaseUser = it.result!!.user!!

                        val user = User(
                            firebaseUser.uid,
                            firstName,
                            lastName,
                            email
                        )

                        FirestoreClass().signupUser(this@SignUpActivity, user)

                        FirebaseAuth.getInstance().signOut()
                        startLoginActivity()

                    } else {
                        showSnackBar(it.exception!!.message.toString(), true)
                    }
                }

        }
    }
    fun userSignupSuccess(){
            Toast.makeText(this,resources.getString(R.string.successSignUp),
                Toast.LENGTH_LONG).show()
        }
}