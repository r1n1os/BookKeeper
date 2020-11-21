package com.example.bookkeeper.main_flow.books_search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import com.example.bookkeeper.BookKeeperApplication
import com.example.bookkeeper.R
import com.example.bookkeeper.base_classes.BaseFragment
import com.example.bookkeeper.main_flow.books_search.adapter.BookSearchAdapter
import kotlinx.android.synthetic.main.fragment_books_search.*

class BooksSearchFragment : BaseFragment<BookSearchViewModel>(), SearchView.OnQueryTextListener {

    private lateinit var searchBookAdapter: BookSearchAdapter
    private var bookSearchViewModel = BookSearchViewModel(BookKeeperApplication.getInstance())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_books_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewAndData()
    }

    private fun initViewAndData() {
        initAdapter()
        initSearchViewListener()
        getObservableForBooks()
    }

    private fun initAdapter() {
        searchBookAdapter = BookSearchAdapter()
        booksResultRecyclerView.adapter = searchBookAdapter
    }

    private fun getObservableForBooks() {
        bookSearchViewModel.booksResult.observe(this.requireActivity(), Observer {books ->
            searchBookAdapter.loadSearchedBooks(books.toMutableList())
        })
    }

    private fun initSearchViewListener() {
        bookSearch.setOnQueryTextListener(this)
    }

    /**
     * Search view Listeners
     * */
    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { searchedBookName ->
            bookSearchViewModel.getSearchedBook(searchedBookName)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean { return false }
}