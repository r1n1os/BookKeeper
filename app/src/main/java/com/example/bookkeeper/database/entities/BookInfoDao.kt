package com.example.bookkeeper.database.entities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface BookInfoDao {
    @Insert
    suspend fun insertListOfBookInfo(bookInfo: MutableList<BookInfoEntity>)

    @Query("DELETE FROM BookInfoEntity")
    suspend fun deleteAll()

    @Transaction
    suspend fun updateBookInfo(bookInfo: MutableList<BookInfoEntity>){
        deleteAll()
        insertListOfBookInfo(bookInfo)
    }
}