package com.example.assignment3.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.example.assignment3.databinding.ActivityBookListAdminBinding
import com.example.assignment3.models.Book
import com.google.firebase.database.*

class BookListAdminActivity : SnackBarActivity() {

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

        binding.descending.setOnClickListener {
            bookList.sortByDescending { list -> list.cost }
            updateBookListAdapter()
        }

        binding.ascending.setOnClickListener {
            bookList.sortWith(compareBy { it.cost })
            updateBookListAdapter()
        }

        binding.clear.setOnClickListener {
            loadBookList()
        }

        binding.searchBook.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                try {
                    adapterBookAdmin.filter.filter(s)
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
        showWaitDialog()

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
                updateBookListAdapter()
                hideWaitDialog()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun updateBookListAdapter(){
        adapterBookAdmin = AdapterBookAdmin(this@BookListAdminActivity,bookList)
        binding.bookList.adapter = adapterBookAdmin
    }
}