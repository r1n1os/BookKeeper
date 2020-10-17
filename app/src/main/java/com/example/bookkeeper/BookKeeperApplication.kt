package com.example.bookkeeper

import android.app.Application
import com.example.bookkeeper.database.BookKeeperDatabase

class BookKeeperApplication: Application() {

    companion object{
        private lateinit var mInstance: BookKeeperApplication

        fun getInstance() = mInstance
    }

    override fun onCreate() {
        super.onCreate()
        mInstance = this
    }

    fun getDatabaseInstance(): BookKeeperDatabase = BookKeeperDatabase(this)
}