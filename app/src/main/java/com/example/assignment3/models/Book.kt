package com.example.assignment3.models

class Book {
    var uid: String = ""
    var title: String = ""
    var description: String = ""
    var cost: Int = 0

    constructor()

    constructor(
        uid: String,
        title: String,
        description: String,
        cost: Int
    ){
        this.uid = uid
        this.title = title
        this.description = description
        this.cost = cost
    }

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "title" to title,
            "description" to description,
            "cost" to cost
        )
    }

}