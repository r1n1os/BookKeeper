package com.example.bookkeeper.database.entities.bojos

import androidx.room.Embedded
import androidx.room.Relation
import com.example.bookkeeper.database.entities.BookInfoEntity
import com.example.bookkeeper.database.entities.ImagesEntity

class BookInfoAndImagesBojo(
    @Embedded var bookInfo: BookInfoEntity,
    @Relation(parentColumn = "bookInfoId", entityColumn = "booksInfoId")
    var imagesEntity: ImagesEntity
) {
}