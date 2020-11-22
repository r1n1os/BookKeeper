   package com.example.bookkeeper.database.entities

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.example.bookkeeper.database.entities.type_converters.AuthorsTypeConverter
import com.example.bookkeeper.database.entities.type_converters.ImagesTypeConverter
import com.google.gson.annotations.SerializedName
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
    @TypeConverters(ImagesTypeConverter::class)
    @SerializedName("imageLinks")
    var imageLinks: ImagesEntity?,
  /*  @TypeConverters(AuthorsTypeConverter::class)
    @SerializedName("authors")
    var authors: MutableList<AuthorsEntity>,*/
    @ForeignKey
        (entity = BooksEntity::class,
            parentColumns = ["id"],
        childColumns = ["bookId"],
        onDelete = CASCADE
    )
    var bookId: String
) {
}