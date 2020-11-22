package com.example.bookkeeper.main_flow.book_details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.bookkeeper.BookKeeperApplication
import com.example.bookkeeper.R
import com.example.bookkeeper.base_classes.BaseFragment
import com.example.bookkeeper.main_flow.books_search.BookSearchViewModel
import com.example.bookkeeper.main_flow.books_search.BooksSearchFragment.Companion.SELECTED_BOOK_ID

class BookDetailsFragment : BaseFragment<BookDetailsViewModel>() {

    private var bookDetailsViewMode = BookDetailsViewModel(BookKeeperApplication.getInstance())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_book_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewAndData()
    }

    private fun initViewAndData() {
        getIntentValues()
        getObservableForRequestedBook()
    }

    private fun getIntentValues() {
        val selectedBookId = arguments?.getString(SELECTED_BOOK_ID)
        selectedBookId?.let { id ->
            bookDetailsViewMode.getBookDetails(id)
        }
    }

    private fun getObservableForRequestedBook() {
        bookDetailsViewMode.selectedBook.observe(this.requireActivity(), Observer { selectedBook ->
        })
    }
}