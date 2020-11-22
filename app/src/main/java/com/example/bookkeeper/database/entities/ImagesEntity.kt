package com.example.bookkeeper.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

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
}