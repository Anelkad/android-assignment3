package com.example.assignment3.activities

import android.widget.Filter
import com.example.assignment3.models.Book

class FilterBookAdmin: Filter {

    val filterList: ArrayList<Book>

    val adapterBookAdmin: AdapterBookAdmin

    constructor(filterList: ArrayList<Book>, adapterBookAdmin: AdapterBookAdmin) {
        this.filterList = filterList
        this.adapterBookAdmin = adapterBookAdmin
    }

    override fun performFiltering(constraint: CharSequence?): FilterResults{
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
        adapterBookAdmin.bookList = results.values as ArrayList<Book>

        adapterBookAdmin.notifyDataSetChanged()
    }


}