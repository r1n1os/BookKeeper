package com.example.bookkeeper.database.entities

import androidx.room.*
import com.example.bookkeeper.database.entities.bojos.BooksAndBookDetailsBojo

@Dao
interface BooksDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOfBooks(books: MutableList<BooksEntity>)

    @Insert
    suspend  fun insertBook(books: BooksEntity)

    @Query("DELETE FROM BooksEntity")
    suspend fun deleteAll()

    @Query("SELECT * FROM BooksEntity WHERE id=:selectedBookId")
    suspend fun getBookById(selectedBookId: String): BooksEntity

    @Transaction
    @Query("SELECT * FROM BooksEntity")
    suspend fun getAllBooks(): List<BooksAndBookDetailsBojo>

    @Transaction
    suspend fun updateData(searchedBooks: MutableList<BooksEntity>){
        deleteAll()
        insertListOfBooks(searchedBooks)
    }
}