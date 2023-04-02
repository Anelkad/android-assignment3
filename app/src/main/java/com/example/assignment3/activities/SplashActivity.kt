package com.example.assignment3.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.assignment3.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }
    fun shopNow(view: View){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}