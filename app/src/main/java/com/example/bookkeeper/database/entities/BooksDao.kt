package com.example.bookkeeper.database.entities

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface BooksDao {
    @Insert
    fun insertListOfBooks(books: MutableList<BooksEntity>)
}