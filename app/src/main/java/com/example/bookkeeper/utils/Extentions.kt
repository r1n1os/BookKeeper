package com.example.bookkeeper.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadUrlImage(imageUrl: String, context: Context){
    Glide.with(context)
        .load(imageUrl)
        .into(this)
}