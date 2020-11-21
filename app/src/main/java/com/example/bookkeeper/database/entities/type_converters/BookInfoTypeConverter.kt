package com.example.bookkeeper.database.entities.type_converters

import androidx.room.TypeConverter
import com.example.bookkeeper.database.entities.BookInfoEntity
import com.google.gson.Gson


class BookInfoTypeConverter {

    @TypeConverter
    fun listToJson(value: BookInfoEntity): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): BookInfoEntity{
        val objects = Gson().fromJson(value, BookInfoEntity::class.java)
        return objects
    }
}