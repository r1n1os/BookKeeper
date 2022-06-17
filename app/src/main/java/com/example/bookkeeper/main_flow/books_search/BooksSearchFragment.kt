package com.example.bookkeeper.main_flow.books_search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.bookkeeper.BookKeeperApplication
import com.example.bookkeeper.R
import com.example.bookkeeper.base_classes.BaseFragment
import com.example.bookkeeper.databinding.FragmentBooksSearchBinding
import com.example.bookkeeper.main_flow.books_search.adapter.BookSearchAdapter
import kotlinx.android.synthetic.main.fragment_books_search.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BooksSearchFragment : BaseFragment<BookSearchViewModel>(), SearchView.OnQueryTextListener,
    BookSearchAdapter.OnBookSearchListener {

    private var bookSearchBinding: FragmentBooksSearchBinding? = null

    companion object{
        const val SELECTED_BOOK_ID = "selected_book_id"
    }

    private lateinit var searchBookAdapter: BookSearchAdapter
    private lateinit var navController: NavController
    private var bookSearchViewModel = BookSearchViewModel(BookKeeperApplication.getInstance())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_books_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bookSearchBinding = FragmentBooksSearchBinding.bind(view)
        navController = Navigation.findNavController(view)
        initViewAndData()
    }

    override fun onDestroyView() {
        bookSearchBinding = null
        super.onDestroyView()
    }
    private fun initViewAndData() {
        initAdapter()
        initSearchViewListener()
        getObservables()
        makeRequestToGetLastSearchedItems()
    }

    private fun initAdapter() {
        searchBookAdapter = BookSearchAdapter(this)
        bookSearchBinding?.booksResultRecyclerView?.adapter = searchBookAdapter
    }

    private fun initSearchViewListener() {
        bookSearch.setOnQueryTextListener(this)
    }

    private fun getObservables() {
        getObservableForBooks()
        getObservableForErrors()
    }

    private fun getObservableForBooks() {
        viewLifecycleOwner.lifecycleScope.launch {
            bookSearchViewModel.booksResult.collect { books ->
                searchBookAdapter.loadSearchedBooks(books.toMutableList())
            }
        }
        /*bookSearchViewModel.booksResult.observe(this.requireActivity(), Observer {books ->

        })*/
    }

    private fun getObservableForErrors(){
        viewLifecycleOwner.lifecycleScope.launch {
            bookSearchViewModel.errorResponse.collect {errorMessage ->
                showToast(errorMessage)
            }
        }
        /*bookSearchViewModel.errorResponse.observe(this.requireActivity(),{errorMessage ->

        })*/
    }

    private fun makeRequestToGetLastSearchedItems(){
        bookSearchViewModel.getPreviousSearchedItemsFromDatabase()
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

    override fun onBookSelected(bookId: String) {
        val bundle = bundleOf(SELECTED_BOOK_ID to bookId)
        navController.navigate(R.id.action_booksSearchFragment_to_bookDetailsFragment, bundle)
    }
}