   package com.example.bookkeeper.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Ignore
import androidx.room.PrimaryKey
import org.jetbrains.annotations.Nullable

@Entity
data class BookInfoEntity(
    @PrimaryKey(autoGenerate = true)
    var bookInfoId: Long,
    var title: String,
    var subtitle: String?,
    var publisher: String?,
    var publishedDate: String?,
    var description: String?,
    var pageCount: Int?,
    var printType: String?,
    var averageRating: Double?,
    var language: String?,
    @ForeignKey
        (entity = BooksEntity::class,
            parentColumns = ["id"],
        childColumns = ["bookId"],
        onDelete = CASCADE
    )
    var bookId: String
) {
}