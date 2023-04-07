package com.example.assignment3.activities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment3.databinding.BookRowAdminBinding
import com.example.assignment3.models.Book
import com.google.firebase.database.FirebaseDatabase

class AdapterBookAdmin: RecyclerView.Adapter<AdapterBookAdmin.HolderBookAdmin>, Filterable {

    lateinit var binding: BookRowAdminBinding

    var context: Context
    var bookList: ArrayList<Book>
    var filterList: ArrayList<Book>

    private var filter: FilterBookAdmin? = null

    constructor(context: Context, bookArrayList: ArrayList<Book>) : super() {
        this.context = context
        this.filterList = bookArrayList
        this.bookList = bookArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderBookAdmin {
        binding = BookRowAdminBinding.inflate(LayoutInflater.from(context), parent, false)
        return HolderBookAdmin(binding.root)
    }

    inner class HolderBookAdmin(itemView: View): RecyclerView.ViewHolder(itemView){
        val title = binding.bookTitle
        val description = binding.bookDescription
        val cost = binding.bookCost
        val more = binding.more

    }

    override fun onBindViewHolder(holder: HolderBookAdmin, position: Int) {
        val book = bookList[position]
        val id = book.uid
        val title = book.title
        val description = book.description
        val cost = book.cost

        holder.title.text = title
        holder.description.text = description
        holder.cost.text = cost.toString()

        holder.more.setOnClickListener {
             moreOptionsDialog(book,holder)
        }

    }

    private fun moreOptionsDialog(book: Book, holder: AdapterBookAdmin.HolderBookAdmin) {
        val id = book.uid
        val title = book.title

        val options = arrayOf("Edit", "Delete")

        val builder = AlertDialog.Builder(context)
        builder.setTitle("Choose option")
            .setItems(options){dialog, position ->
                if (position == 0){
                    //Edit
                    val intent = Intent(context,EditBookActivity::class.java)
                    intent.putExtra("id",id)
                    context.startActivity(intent)
                }
                else if (position==1){
                    //Delete
                    deleteBook(context, id)
                }
            }
            .show()
    }

    private fun deleteBook(context: Context, id: String,){
        val database = FirebaseDatabase.getInstance("https://assignment3-afd18-default-rtdb.europe-west1.firebasedatabase.app").getReference("books")
        database.child(id)
            .removeValue()
            .addOnSuccessListener {
                Toast.makeText(context,"Book deleted!",
                    Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener{ e->
                Log.d("book delete","Error: ${e.message}")
                Toast.makeText(context,"Cannot delete book!",
                    Toast.LENGTH_LONG).show()
            }
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun getFilter(): Filter {
        if (filter==null){
            filter = FilterBookAdmin(filterList,this)
        }
        return filter as FilterBookAdmin
    }
}