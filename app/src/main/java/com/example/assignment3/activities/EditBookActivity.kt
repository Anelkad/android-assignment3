package com.example.assignment3.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.assignment3.R
import com.example.assignment3.databinding.ActivityEditBookBinding
import com.example.assignment3.models.Book
import com.google.firebase.database.*

class EditBookActivity : SnackBarActivity() {

    lateinit var binding: ActivityEditBookBinding

    var id = ""
    lateinit var title: String
    lateinit var description: String
    lateinit var cost: String

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.getStringExtra("id")!!

        loadBookInfo()

        binding.submit.setOnClickListener {
            editBook()
            finish()
        }

        binding.back.setOnClickListener {
            finish()
        }
    }

    private fun loadBookInfo() {
        database = FirebaseDatabase.getInstance("https://assignment3-afd18-default-rtdb.europe-west1.firebasedatabase.app").getReference("books")
        database.child(id)
            .addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val title = snapshot.child("title").value.toString()
                    val description = snapshot.child("description").value.toString()
                    val cost = snapshot.child("cost").value.toString()

                    binding.title.setText(title)
                    binding.description.setText(description)
                    binding.cost.setText(cost)

                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

    private fun editBook() {
        showWaitDialog()

        if (validateBookFields()){

            val book = Book(
                id,
                title,
                description,
                cost.toInt()
            )

            database.child(id).updateChildren(book.toMap())
                .addOnSuccessListener {
                    hideWaitDialog()
                    Toast.makeText(this,"Book updated!",
                        Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener { e->
                    Log.d("book edit","Error: ${e.message}")
                    Toast.makeText(this,"Cannot edit book!",
                        Toast.LENGTH_LONG).show()
                }
        }
    }

    private fun validateBookFields(): Boolean{
        title = binding.title.text.toString()
        description = binding.description.text.toString()
        cost = binding.cost.text.toString()

        return when{
            title.isEmpty() -> {
                hideWaitDialog()
                showSnackBar("Please enter title!",true)
                false
            }
            description.isEmpty() -> {
                hideWaitDialog()
                showSnackBar("Please enter description!",true)
                false
            }
            cost.isEmpty() -> {
                hideWaitDialog()
                showSnackBar("Please enter cost!",true)
                false
            }
            !cost.all { char -> char.isDigit() } ->{
                hideWaitDialog()
                showSnackBar("Cost is not numeric!",true)
                false
            }
            else -> {
                //showSnackBar("Success",false)
                true
            }
        }
    }

}