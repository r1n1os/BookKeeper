package com.example.bookkeeper.database.entities

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface BookInfoDao {
    @Insert
    fun insertListOfBookInfo(bookInfo: MutableList<BookInfoEntity>)
}