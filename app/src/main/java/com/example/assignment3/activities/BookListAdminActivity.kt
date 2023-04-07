package com.example.assignment3.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.TextView
import com.example.assignment3.databinding.ActivityBookListAdminBinding
import com.example.assignment3.models.Book
import com.google.firebase.database.*

class BookListAdminActivity : AppCompatActivity() {

    lateinit var binding: ActivityBookListAdminBinding

    lateinit var bookList: ArrayList<Book>
    lateinit var adapterBookAdmin: AdapterBookAdmin

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookListAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            finish()
        }

        loadBookList()

        binding.searchBook.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                try {
                    adapterBookAdmin.filter!!.filter(s)
                }
                catch (e:Exception){
                    Log.d("text change","onTextChange: ${e.message}")
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun loadBookList() {
        bookList = ArrayList()

        database = FirebaseDatabase.getInstance("https://assignment3-afd18-default-rtdb.europe-west1.firebasedatabase.app").getReference("books")

        database.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                bookList.clear()
                for (ds in snapshot.children){
                    val book = ds.getValue(Book::class.java)

                    if (book!=null){
                        bookList.add(book)
                    }
                }
                adapterBookAdmin = AdapterBookAdmin(this@BookListAdminActivity,bookList)
                binding.bookList.adapter = adapterBookAdmin
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}