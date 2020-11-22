package com.example.bookkeeper.database.entities.type_converters

import androidx.room.TypeConverter
import com.example.bookkeeper.database.entities.ImagesEntity
import com.google.gson.Gson

class ImagesTypeConverter {
    @TypeConverter
    fun listToJson(imageEntity: ImagesEntity?): String? {
        return Gson().toJson(imageEntity)
    }

    @TypeConverter
    fun jsonToList(imageEntityObject: String?): ImagesEntity? {
        val objects = Gson().fromJson(imageEntityObject, ImagesEntity::class.java)
        return objects
    }
}