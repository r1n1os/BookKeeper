package com.example.bookkeeper.main_flow.book_details

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bookkeeper.BookKeeperApplication
import com.example.bookkeeper.base_classes.BaseViewModel
import com.example.bookkeeper.database.entities.BooksEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookDetailsViewModel(application: Application): BaseViewModel(application){

    val selectedBook = MutableLiveData<BooksEntity>()

    fun getBookDetails(bookId: String){
        launch {
            executeLocalQueryToGetSelectedDetails(bookId).collect { book ->
                selectedBook.value = book
            }
        }
    }

    private fun executeLocalQueryToGetSelectedDetails(bookId: String) = flow {
        var selectedBook: BooksEntity? = null
        withContext(Dispatchers.IO) {
            selectedBook = BookKeeperApplication.getInstance().getDatabaseInstance().booksDao().getBookById(bookId)
        }
        emit(selectedBook)
    }
}