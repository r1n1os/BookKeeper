package com.example.bookkeeper.database.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.bookkeeper.database.entities.type_converters.BookInfoTypeConverter
import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.Nullable

@Entity
data class BooksEntity(
    @PrimaryKey val id: String,
    val selfLink: String,
    @TypeConverters(BookInfoTypeConverter::class)
    @SerializedName("volumeInfo")
    var volumeInfo: BookInfoEntity
){}