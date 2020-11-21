package com.example.bookkeeper.database.entities

import com.google.gson.annotations.SerializedName

data class VolumesEntity(
                  var totalItems: Int,
                  var kind: String,
                  @SerializedName("items")
var books: MutableList<BooksEntity>) {

}