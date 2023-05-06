package com.example.assignment3.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.assignment3.databinding.ActivityBookAddBinding
import com.example.assignment3.models.Book
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddBookActivity : SnackBarActivity() {

    lateinit var title: String
    lateinit var description: String
    lateinit var cost: String

    private lateinit var database: DatabaseReference

    lateinit var binding: ActivityBookAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addBook.setOnClickListener {
            addBook()
        }

        binding.back.setOnClickListener {
            finish()
        }

    }

    private fun addBook(){

        showWaitDialog()

        if (validateBookFields()){

            database = FirebaseDatabase.getInstance("https://assignment3-afd18-default-rtdb.europe-west1.firebasedatabase.app").getReference("books")
            val id = database.push().key

            val book = Book(
                id!!,
                title,
                description,
                cost.toInt()
            )

            database.child(id).setValue(book)
                .addOnSuccessListener {
                    hideWaitDialog()
                    Toast.makeText(this,"Book added!",
                        Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener { e->
                Log.d("book add","Error: ${e.message}")
                Toast.makeText(this,"Cannot add book!",
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