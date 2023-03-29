 package com.example.assignment3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

open class SnackBarActivity : AppCompatActivity() {

    fun showSnackBar(message: String, isError: Boolean){
        val snackBar = Snackbar.make(findViewById(android.R.id.content),message,Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view
        snackBar.setTextColor(getColor(R.color.white))

        if (isError){
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(this,R.color.colorSnackBarError)
            )
        }
        else{
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(this,R.color.colorSnackBarSuccess)
            )
        }
        snackBar.show()
    }
}