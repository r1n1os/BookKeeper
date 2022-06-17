package com.example.bookkeeper.main_flow.book_details

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bookkeeper.BookKeeperApplication
import com.example.bookkeeper.base_classes.BaseViewModel
import com.example.bookkeeper.database.entities.BookInfoEntity
import com.example.bookkeeper.database.entities.BooksEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookDetailsViewModel(application: Application): BaseViewModel(application){

    val tempBookInfoEntity = BookInfoEntity(-1L, "", "", "", "", "", -1, "", 0.0, "", null, "")
    val selectedBook = MutableStateFlow<BooksEntity>(BooksEntity("", "", tempBookInfoEntity))

    fun getBookDetails(bookId: String){
        launch {
            executeLocalQueryToGetSelectedDetails(bookId).collect { book ->
                book.let {currentBook ->
                    selectedBook.value = currentBook
                }
            }
        }
    }

    private fun executeLocalQueryToGetSelectedDetails(bookId: String) = flow {
        emit(BookKeeperApplication.getInstance().getDatabaseInstance().booksDao().getBookById(bookId))
        /*var selectedBook: BooksEntity? = null
        withContext(Dispatchers.IO) {
            selectedBook = BookKeeperApplication.getInstance().getDatabaseInstance().booksDao().getBookById(bookId)
        }
        emit(selectedBook)*/
    }.flowOn(Dispatchers.Main)
}