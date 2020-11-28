package com.example.bookkeeper.main_flow.books_search.model

import com.example.bookkeeper.BookKeeperApplication
import com.example.bookkeeper.R
import com.example.bookkeeper.database.entities.bojos.BooksAndBookDetailsBojo
import com.example.bookkeeper.main_flow.books_search.model.SearchedBooksModel.BooksViewType.*

data class SearchedBooksModel(
    var title: String,
    var bookDetails: BooksAndBookDetailsBojo?,
    var booksViewType: BooksViewType
) {

    enum class BooksViewType {
        TITLE_SECTION,
        BOOKS_SECTION,
        EMPTY_VIEW_SECTION
    }

    companion object{
        fun createBookSearchModel(books: MutableList<BooksAndBookDetailsBojo>, loadPreviousSearch: Boolean): MutableList<SearchedBooksModel>{
            var searchedBooksModel = mutableListOf<SearchedBooksModel>()

            if (books.size > 0 && loadPreviousSearch) {
                searchedBooksModel.add(SearchedBooksModel(BookKeeperApplication.getInstance().getString(R.string.previous_searched_books), null, TITLE_SECTION))
                searchedBooksModel = addBooksToList(searchedBooksModel, books)
            } else if (books.size > 0){
                searchedBooksModel = addBooksToList(searchedBooksModel, books)
            } else {
                searchedBooksModel.add(SearchedBooksModel("", null, EMPTY_VIEW_SECTION))
            }

            return searchedBooksModel
        }

        private fun addBooksToList(searchedBooksModelList: MutableList<SearchedBooksModel>, books: MutableList<BooksAndBookDetailsBojo>): MutableList<SearchedBooksModel>{
            books.forEach {book ->
                searchedBooksModelList.add(SearchedBooksModel("", book, BOOKS_SECTION))
            }
            return searchedBooksModelList
        }
    }
}