package com.example.bookkeeper.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = BookInfoEntity::class,
    parentColumns = ["bookInfoId"],
    childColumns = ["booksInfoId"],
    onDelete = ForeignKey.CASCADE
)])
data class AuthorsEntity(
    @PrimaryKey(autoGenerate = true) var authorsId: Long,
    var author: String?,
    var booksInfoId: Long
) {}