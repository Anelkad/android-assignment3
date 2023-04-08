package com.example.assignment3.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.assignment3.R
import com.example.assignment3.databinding.ActivityBookDetailsBinding
import com.google.firebase.database.*

class BookDetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityBookDetailsBinding

    private lateinit var database: DatabaseReference
    var id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.getStringExtra("id")!!

        loadBookInfo()

        binding.back.setOnClickListener {
            finish()
        }
    }

    private fun loadBookInfo() {
        database = FirebaseDatabase.getInstance("https://assignment3-afd18-default-rtdb.europe-west1.firebasedatabase.app").getReference("books")
        database.child(id)
            .addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val title = snapshot.child("title").value.toString()
                    val description = snapshot.child("description").value.toString()
                    val cost = snapshot.child("cost").value.toString()

                    binding.title.text = "Book title: ${title}"
                    binding.description.text = description
                    binding.cost.text = "${cost}$"

                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }
}