package com.example.bookkeeper.database.entities.bojos

import androidx.room.Embedded
import androidx.room.Relation
import com.example.bookkeeper.database.entities.BookInfoEntity
import com.example.bookkeeper.database.entities.BooksEntity
data class BooksAndBookDetailsBojo(
    @Embedded
    var book: BooksEntity,
    @Relation(parentColumn = "id", entityColumn = "bookId")
    var booksInfo: BookInfoEntity)
 {
 }