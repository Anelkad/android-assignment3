package com.example.assignment3.activities

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.assignment3.R
import com.example.assignment3.databinding.FragmentBooksUserBinding
import com.example.assignment3.models.Book
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class BooksUserFragment : Fragment {

    private lateinit var waitDialog: Dialog

    lateinit var binding: FragmentBooksUserBinding

    private lateinit var bookList: ArrayList<Book>
    private lateinit var adapterBookUser: AdapterBookUser

    constructor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentBooksUserBinding.inflate(LayoutInflater.from(context),container,false)

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

        binding.searchBook.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                try {
                    adapterBookUser.filter.filter(s)
                }
                catch (e:Exception){
                    Log.d("text change","onTextChange: ${e.message}")
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        return binding.root
    }

    private fun loadBookList() {

        showWaitDialog()

        bookList = ArrayList()

        var database = FirebaseDatabase.getInstance("https://assignment3-afd18-default-rtdb.europe-west1.firebasedatabase.app").getReference("books")

        database.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                bookList.clear()
                for (ds in snapshot.children){
                    val book = ds.getValue(Book::class.java)
                    bookList.add(book!!)
                }
                updateBookListAdapter()
                hideWaitDialog()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun updateBookListAdapter(){
        adapterBookUser = AdapterBookUser(requireContext(),bookList)
        binding.bookList.adapter = adapterBookUser
    }

    fun showWaitDialog(){
        waitDialog = Dialog(requireContext())
        waitDialog.setContentView(R.layout.wait_dialog)

        waitDialog.setCancelable(false)
        waitDialog.setCanceledOnTouchOutside(false)

        waitDialog.show()
    }

    fun hideWaitDialog(){
        waitDialog.dismiss()
    }
}