package com.example.bookkeeper.network

import com.example.bookkeeper.database.entities.VolumesEntity
import com.example.bookkeeper.utils.Constants.API_KEY
import com.example.bookkeeper.utils.Urls.SEARCH_BOOK_URL
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface BooksApi {

    @Headers("key: $API_KEY")
    @GET(SEARCH_BOOK_URL)
    suspend fun getSearchedBooks(@Query("q") searchQuery: String): Response<VolumesEntity>
}