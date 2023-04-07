package com.example.assignment3.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.assignment3.R
import com.example.assignment3.firestore.FirestoreClass
import com.example.assignment3.models.User

class DashboardUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_user)

        FirestoreClass().getUserDetails(this)

    }

    fun getDashboard(user: User){
        val email = findViewById<TextView>(R.id.email)
        email.text = user.email
    }
}