package com.example.bookkeeper.database.entities.bojos

import androidx.room.Embedded
import androidx.room.Relation
import com.example.bookkeeper.database.entities.AuthorsEntity
import com.example.bookkeeper.database.entities.BookInfoEntity

data class BookInfoAndAuthorsBojo(
    @Embedded var bookInfo: BookInfoEntity,
    @Relation(parentColumn = "bookInfoId", entityColumn = "booksInfoId")
    var authors: MutableList<AuthorsEntity>
) {}