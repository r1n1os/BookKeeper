package com.example.bookkeeper.main_flow.books_search

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.bookkeeper.BookKeeperApplication
import com.example.bookkeeper.base_classes.BaseViewModel
import com.example.bookkeeper.database.entities.*
import com.example.bookkeeper.database.entities.bojos.BooksAndBookDetailsBojo
import com.example.bookkeeper.network.RetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookSearchViewModel(application: Application) : BaseViewModel(application) {

    private var retrofitService = RetrofitService()
    var booksResult = MutableLiveData<List<BooksAndBookDetailsBojo>>()
    /*var bookResult : LiveData<TestEntity> = liveData(Dispatchers.IO){
        val result = retrofitService.getRetrofitService().getSearchedBooks("Clean Code")
        emit(result)
    }*/

    fun getSearchedBook(searchBookName: String){
        launch {
            executeRequestForSearchedBook(searchBookName).collect {
                booksResult.value = it
            }
        }
    }

   private fun executeRequestForSearchedBook(searchKeyword: String) = flow{
       var loadBooks = mutableListOf<BooksAndBookDetailsBojo>()
        withContext(Dispatchers.IO){
            val bookResponse = retrofitService.getRetrofitService().getSearchedBooks(searchKeyword)
            val booksInfo = mutableListOf<BookInfoEntity>()
            bookResponse.books.forEach {
                it.volumeInfo.bookId = it.id
                booksInfo.add(it.volumeInfo)
            }
            BookKeeperApplication.getInstance().getDatabaseInstance().booksDao().updateData(bookResponse.books)
            BookKeeperApplication.getInstance().getDatabaseInstance().bookInfoDao().updateBookInfo(booksInfo)
            booksInfo.forEach {bookInfo ->
                bookInfo.imageLinks?.let { image ->
                    var tempImage = image
                    tempImage.bookDetailsId = bookInfo.bookInfoId
                    BookKeeperApplication.getInstance().getDatabaseInstance().imagesDao().updateBookInfo(tempImage)
                }
            }
            loadBooks = BookKeeperApplication.getInstance().getDatabaseInstance().booksDao().getAllBooks().toMutableList()
        }
       emit(loadBooks)
    }

    fun getPreviousSearchedItemsFromDatabase(){
        launch {
            executeLocalRequestToGetLastSearchedItems().collect { foundBooks ->
                booksResult.value = foundBooks
            }
        }
    }

    private fun executeLocalRequestToGetLastSearchedItems() = flow {
        var books = mutableListOf<BooksAndBookDetailsBojo>()
        withContext(Dispatchers.IO){
             books = BookKeeperApplication.getInstance().getDatabaseInstance().booksDao().getAllBooks().toMutableList()
        }
        emit(books)
    }
}