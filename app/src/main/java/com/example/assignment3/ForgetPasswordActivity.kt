package com.example.assignment3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ForgetPasswordActivity : SnackBarActivity() {
    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        val submit: View = findViewById(R.id.submit)
        submit.setOnClickListener{
            sendEmail()
        }


    }
    private fun sendEmail(){

        email = findViewById<TextView>(R.id.email).text.toString()

        if(email.isEmpty()) {
            showSnackBar(resources.getString(R.string.emailIsEmpty),true)}
        else{
            showWaitDialog()

            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener{
                    hideWaitDialog()
                    if (it.isSuccessful){
                        Toast.makeText(this,resources.getString(R.string.emailIsSend),
                            Toast.LENGTH_LONG).show()
                        finish()
                    } else {
                        showSnackBar(it.exception!!.message.toString(),true)
                    }
                }
        }
    }


}