package com.example.assignment3.activities

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment3.databinding.BookRowUserBinding
import com.example.assignment3.models.Book

class AdapterBookUser: RecyclerView.Adapter<AdapterBookUser.HolderBookUser>, Filterable {


    lateinit var binding: BookRowUserBinding

    var context: Context
    var bookList: ArrayList<Book>
    var filterList: ArrayList<Book>


    private var filter: FilterBookUser? = null

    constructor(context: Context, bookArrayList: ArrayList<Book>){
        this.context = context
        this.bookList = bookArrayList
        this.filterList = bookArrayList
    }


    inner class HolderBookUser(itemView: View): RecyclerView.ViewHolder(itemView){
        val title = binding.bookTitle
        val description = binding.bookDescription
        val cost = binding.bookCost
        val itemView = binding.itemView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderBookUser {
        binding = BookRowUserBinding.inflate(LayoutInflater.from(context),parent,false)

        return HolderBookUser(binding.root)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onBindViewHolder(holder: HolderBookUser, position: Int) {
        val book = bookList[position]
        val id = book.uid
        val title = book.title
        val description = book.description
        val cost = book.cost

        holder.title.text = title
        holder.description.text = description
        holder.cost.text = cost.toString()

        holder.itemView.setOnClickListener {
            val intent = Intent(context,BookDetailsActivity::class.java)
            intent.putExtra("id",id)
            context.startActivity(intent)
        }

    }

    override fun getFilter(): Filter {
        if (filter==null){
            filter = FilterBookUser(filterList,this)
        }
        return filter as FilterBookUser
    }
}