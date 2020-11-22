package com.example.bookkeeper.database.entities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface ImagesDao {

    @Insert
    suspend fun insertImageObject(imageEntity: ImagesEntity)

    @Query("DELETE FROM ImagesEntity")
    suspend fun deleteAll()

    @Transaction
    suspend fun updateBookInfo(imageEntity: ImagesEntity){
        deleteAll()
        insertImageObject(imageEntity)
    }
}