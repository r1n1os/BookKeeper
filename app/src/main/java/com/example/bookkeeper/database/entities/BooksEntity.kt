package com.example.bookkeeper.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BooksEntity(
    @PrimaryKey val id: String,
    val selfLink: String)