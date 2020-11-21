package com.example.bookkeeper

import android.app.Application
import com.example.bookkeeper.database.BookKeeperDatabase

class BookKeeperApplication: Application() {

    companion object{
        private lateinit var mInstance: BookKeeperApplication

        fun getInstance() = mInstance
    }

    override fun onCreate() {
        mInstance = this
        super.onCreate()
    }

    fun getDatabaseInstance(): BookKeeperDatabase = BookKeeperDatabase(this)
}