package com.example.bookkeeper.network

import com.example.bookkeeper.utils.Constants.API_KEY
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers

interface BooksApi {

    @Headers("key", API_KEY)
    @GET("")
    suspend fun getBook(): MutableList<String>
}