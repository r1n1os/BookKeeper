package com.example.bookkeeper.database.entities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface AuthorsDao {

    @Insert
   suspend fun insertListOfAuthors(authors: MutableList<AuthorsEntity>)

    @Query("DELETE FROM AuthorsEntity")
   suspend fun deleteAll()

    @Transaction
  suspend  fun updateAuthors(authors: MutableList<AuthorsEntity>){
        deleteAll()
        insertListOfAuthors(authors)
    }
}