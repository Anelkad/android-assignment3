package com.example.assignment3.activities

import android.widget.Filter
import com.example.assignment3.models.Book

class FilterBookUser: Filter {

    val filterList: ArrayList<Book>
    val adapterBookUser: AdapterBookUser

    constructor(filterList: ArrayList<Book>, adapterBookUser: AdapterBookUser) {
        this.filterList = filterList
        this.adapterBookUser = adapterBookUser
    }

    override fun performFiltering(constraint: CharSequence?): FilterResults {
        var constraint: CharSequence? = constraint
        val results = FilterResults()

        if (constraint!=null && constraint.isNotEmpty()){
            constraint = constraint.toString().lowercase()
            var filteredBook = ArrayList<Book>()
            for (i in filterList.indices){
                if (filterList[i].title.lowercase().contains(constraint)){
                    filteredBook.add(filterList[i])
                }
            }
            results.count = filteredBook.size
            results.values = filteredBook
        }
        else{
            results.count = filterList.size
            results.values = filterList
        }
        return results
    }

    override fun publishResults(constraint: CharSequence?, results: FilterResults) {
        adapterBookUser.bookList = results.values as ArrayList<Book>

        adapterBookUser.notifyDataSetChanged()
    }


}