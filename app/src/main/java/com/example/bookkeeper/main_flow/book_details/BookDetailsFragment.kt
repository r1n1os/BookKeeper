package com.example.bookkeeper.main_flow.book_details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.bookkeeper.BookKeeperApplication
import com.example.bookkeeper.R
import com.example.bookkeeper.base_classes.BaseFragment
import com.example.bookkeeper.database.entities.BooksEntity
import com.example.bookkeeper.main_flow.books_search.BookSearchViewModel
import com.example.bookkeeper.main_flow.books_search.BooksSearchFragment.Companion.SELECTED_BOOK_ID
import com.example.bookkeeper.utils.loadUrlImage
import kotlinx.android.synthetic.main.fragment_book_details.*

class BookDetailsFragment : BaseFragment<BookDetailsViewModel>() {

    private lateinit var navController: NavController
    private var bookDetailsViewMode = BookDetailsViewModel(BookKeeperApplication.getInstance())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_book_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        initViewAndData()
    }

    private fun initViewAndData() {
        getIntentValues()
        getObservableForRequestedBook()
        initListeners()
    }

    private fun initListeners() {
        backIcon.setOnClickListener { navController.popBackStack() }
    }

    private fun getIntentValues() {
        val selectedBookId = arguments?.getString(SELECTED_BOOK_ID)
        selectedBookId?.let { id ->
            bookDetailsViewMode.getBookDetails(id)
        }
    }

    private fun getObservableForRequestedBook() {
        bookDetailsViewMode.selectedBook.observe(this.requireActivity(), Observer { selectedBook ->
            setDataToViews(selectedBook)
        })
    }

    private fun setDataToViews(selectedBook: BooksEntity?) {
        selectedBook?.let { book ->
            book.volumeInfo.imageLinks?.smallThumbnail?.let { bookDetailsImage.loadUrlImage(it.replace("http", "https"), this.requireContext()) }
            bookDetailsTitle.text = book.volumeInfo.title
            bookDetailsSubtitle.text = book.volumeInfo.subtitle
            bookRating.text = "4/5"
            bookDescription.text = book.volumeInfo.description
        }
    }
}