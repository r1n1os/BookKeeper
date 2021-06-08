package com.example.bookkeeper.main_flow.books_search

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.bookkeeper.BookKeeperApplication
import com.example.bookkeeper.base_classes.BaseViewModel
import com.example.bookkeeper.database.entities.*
import com.example.bookkeeper.database.entities.bojos.BooksAndBookDetailsBojo
import com.example.bookkeeper.main_flow.books_search.model.SearchedBooksModel
import com.example.bookkeeper.network.RetrofitService
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class BookSearchViewModel(application: Application) : BaseViewModel(application) {

    private var retrofitService = RetrofitService()
    var booksResult = MutableStateFlow<List<SearchedBooksModel>>(mutableListOf())
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
       //var loadBooks = mutableListOf<SearchedBooksModel>()
       // withContext(Dispatchers.IO) {
            val requestResponse = retrofitService.getRetrofitService().getSearchedBooks(searchKeyword)
            if (requestResponse.isSuccessful) {
                val bookResponse = requestResponse.body()
                bookResponse?.books?.let { books ->
                    BooksEntity.insertBooks(books).collect {
                     emit(SearchedBooksModel.createBookSearchModel(it.toMutableList(), false))
                    }
                }
            } else {
                errorResponse.value = requestResponse.message() ?: "Something went wrong"
            }
            //emit(loadBooks)
       // }
    }.flowOn(Dispatchers.Main)
       .catch {
           errorResponse.value = it.message ?: "Something went wrong"
   }

    fun getPreviousSearchedItemsFromDatabase(){
        launch {
            executeLocalRequestToGetLastSearchedItems().collect { foundBooks ->
                booksResult.value = foundBooks
            }
        }
    }

    private fun executeLocalRequestToGetLastSearchedItems() = flow {
        emit(SearchedBooksModel.createBookSearchModel(BookKeeperApplication.getInstance().getDatabaseInstance().booksDao().getAllBooks().toMutableList(), true))

        /*var books = mutableListOf<SearchedBooksModel>()
        withContext(Dispatchers.IO){
        }
        emit(books)*/
    }.flowOn(Dispatchers.Main)
}