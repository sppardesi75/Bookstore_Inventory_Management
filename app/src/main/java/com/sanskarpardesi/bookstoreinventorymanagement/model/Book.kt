package com.sanskarpardesi.bookstoreinventorymanagement.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_table")
data class Book(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val author: String,
    val genre: String,
    val price: Double,
    val quantity: Int,
    val publisher: String = "",
    val isbn: String = ""


)
