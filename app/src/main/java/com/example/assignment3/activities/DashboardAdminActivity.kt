package com.example.assignment3.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.assignment3.R
import com.example.assignment3.firestore.FirestoreClass
import com.example.assignment3.models.User
import com.example.assignment3.databinding.ActivityDashboardAdminBinding
import com.google.firebase.auth.FirebaseAuth

class DashboardAdminActivity : AppCompatActivity() {

    lateinit var binding: ActivityDashboardAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirestoreClass().getUserDetails(this)

        binding.addBook.setOnClickListener{
            startActivity(Intent(this,AddBookActivity::class.java))
        }

        binding.listBook.setOnClickListener {
            startActivity(Intent(this,BookListAdminActivity::class.java))
        }

        binding.logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            Toast.makeText(this,"Successfully Log Out!",
                Toast.LENGTH_LONG).show()

            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }

    fun getDashboard(user: User){
        val email = findViewById<TextView>(R.id.email)
        email.text = user.email
    }


}