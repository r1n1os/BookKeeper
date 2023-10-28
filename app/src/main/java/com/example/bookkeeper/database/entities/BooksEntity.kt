package com.example.bookkeeper.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.bookkeeper.BookKeeperApplication
import com.example.bookkeeper.database.entities.type_converters.BookInfoTypeConverter
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.flow

@Entity
data class BooksEntity(
    @PrimaryKey val id: String,
    val selfLink: String,
    @TypeConverters(BookInfoTypeConverter::class)
    @SerializedName("volumeInfo")
    var volumeInfo: BookInfoEntity
){
    companion object{
        fun insertBooks(books: MutableList<BooksEntity>) = with(BookKeeperApplication.getInstance()){
            flow{
                val booksInfo = mutableListOf<BookInfoEntity>()
                books.forEach { book ->
                    book.volumeInfo.bookId = book.id
                    booksInfo.add(book.volumeInfo)
                }
                getDatabaseInstance().booksDao().updateData(books)
                insertBooksInfo(booksInfo)
                emit(getDatabaseInstance().booksDao().getAllBooks())
            }
        }

        private suspend fun insertBooksInfo(booksInfo: MutableList<BookInfoEntity>) {
            BookInfoEntity.insertBookInfo(booksInfo).collect {}
        }
    }
}