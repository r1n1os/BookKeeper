package com.example.bookkeeper.database.entities

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.example.bookkeeper.BookKeeperApplication
import com.example.bookkeeper.database.entities.type_converters.ImagesTypeConverter
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.flow

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
    companion object {
        suspend fun insertBookInfo(booksInfo: MutableList<BookInfoEntity>) = with(BookKeeperApplication.getInstance()) {
            flow {
                getDatabaseInstance().bookInfoDao().updateBookInfo(booksInfo)
                booksInfo.forEach { bookInfoEntity ->
                    bookInfoEntity.imageLinks?.let { imageEntity ->
                        insertImageLink(imageEntity, bookInfoEntity.bookInfoId)
                    }
                }
                emit(booksInfo)
            }
        }

        private suspend fun insertImageLink(imageEntity: ImagesEntity, bookInfoId: Long) {
            imageEntity.bookDetailsId = bookInfoId
            ImagesEntity.insertImage(imageEntity)
        }
    }
}