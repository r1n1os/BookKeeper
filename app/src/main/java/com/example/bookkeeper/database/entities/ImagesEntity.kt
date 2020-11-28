package com.example.bookkeeper.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.bookkeeper.BookKeeperApplication
import kotlinx.coroutines.flow.flow

@Entity
data class ImagesEntity(
    @PrimaryKey(autoGenerate = true) var id: Long,
    var smallThumbnail: String?,
    var thumbNail: String?,
    @ForeignKey
        (entity = BookInfoEntity::class,
        parentColumns = ["bookInfoId"],
        childColumns = ["bookDetailsId"],
        onDelete = ForeignKey.CASCADE
    )
    var bookDetailsId: Long
) {
    companion object{
        suspend fun insertImage(imagesEntity: ImagesEntity) = with(BookKeeperApplication){
            flow {
                getInstance().getDatabaseInstance().imagesDao().insertImageObject(imagesEntity)
                emit(imagesEntity)
            }
        }
    }
}