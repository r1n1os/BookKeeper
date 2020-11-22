package com.example.bookkeeper.database.entities.type_converters

import androidx.room.TypeConverter
import com.example.bookkeeper.database.entities.AuthorsEntity
import com.google.gson.Gson

class AuthorsTypeConverter {

 /*   @TypeConverter
    fun listToJson(authorsList: List<AuthorsEntity>): String{
        return Gson().toJson(authorsList)
    }

    @TypeConverter
    fun jsonToList(json: String): List<AuthorsEntity>{
        val objects = Gson().fromJson(json, Array<AuthorsEntity>::class.java).asList()
        return objects
    }*/
}