package com.example.bookkeeper.database.entities

import androidx.room.*
import com.example.bookkeeper.database.entities.bojos.BooksAndBookDetailsBojo

@Dao
interface BooksDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListOfBooks(books: MutableList<BooksEntity>)

    @Insert
    fun insertBook(books: BooksEntity)

    @Transaction
    @Query("SELECT * FROM BooksEntity")
    suspend fun getAllBooks(): List<BooksAndBookDetailsBojo>

}