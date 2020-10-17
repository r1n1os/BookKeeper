package com.example.bookkeeper.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bookkeeper.database.entities.BooksDao
import com.example.bookkeeper.database.entities.BooksEntity

@Database(entities= [BooksEntity::class], version = 1)
abstract class BookKeeperDatabase: RoomDatabase() {

    abstract fun booksDao(): BooksDao

    companion object{
        @Volatile
        private var instance: BookKeeperDatabase? = null
        private val LOCK = Any()
        private const val DB_NAME = "Book_keeper_db"

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            BookKeeperDatabase::class.java,
            DB_NAME
        ).build()

        fun destroyInstance() {
            instance = null
        }

        fun deleteAll() {
            instance?.clearAllTables()
        }
    }
}