package com.example.bookkeeper.network

import com.example.bookkeeper.utils.Urls.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {

    fun getRetrofitService() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(BooksApi::class.java)

}