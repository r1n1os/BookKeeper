package com.example.bookkeeper.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.bookkeeper.database.entities.*
import com.example.bookkeeper.database.entities.type_converters.AuthorsTypeConverter
import com.example.bookkeeper.database.entities.type_converters.BookInfoTypeConverter
import com.example.bookkeeper.database.entities.type_converters.ImagesTypeConverter

@Database(entities= [BooksEntity::class, BookInfoEntity::class, AuthorsEntity::class, ImagesEntity::class], version = 1)
@TypeConverters(BookInfoTypeConverter::class, ImagesTypeConverter::class/*, AuthorsTypeConverter::class*/)
abstract class BookKeeperDatabase: RoomDatabase() {

    abstract fun booksDao(): BooksDao
    abstract fun bookInfoDao(): BookInfoDao
    abstract fun imagesDao(): ImagesDao
    //abstract fun authorsDao(): AuthorsDao

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