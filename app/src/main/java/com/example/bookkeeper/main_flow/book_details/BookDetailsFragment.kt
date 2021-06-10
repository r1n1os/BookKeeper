package com.example.bookkeeper.main_flow.book_details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.bookkeeper.BookKeeperApplication
import com.example.bookkeeper.R
import com.example.bookkeeper.base_classes.BaseFragment
import com.example.bookkeeper.database.entities.BooksEntity
import com.example.bookkeeper.databinding.FragmentBookDetailsBinding
import com.example.bookkeeper.main_flow.books_search.BookSearchViewModel
import com.example.bookkeeper.main_flow.books_search.BooksSearchFragment.Companion.SELECTED_BOOK_ID
import com.example.bookkeeper.utils.loadUrlImage
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BookDetailsFragment : BaseFragment<BookDetailsViewModel>() {

    private var bookDetailsBinding: FragmentBookDetailsBinding? = null

    private lateinit var navController: NavController
    private var bookDetailsViewMode = BookDetailsViewModel(BookKeeperApplication.getInstance())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_book_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bookDetailsBinding = FragmentBookDetailsBinding.bind(view)
        navController = Navigation.findNavController(view)
        initViewAndData()
    }

    private fun initViewAndData() {
        getIntentValues()
        getObservableForRequestedBook()
        initListeners()
    }

    private fun initListeners() {
        bookDetailsBinding?.backIcon?.setOnClickListener { navController.popBackStack() }
    }

    private fun getIntentValues() {
        val selectedBookId = arguments?.getString(SELECTED_BOOK_ID)
        selectedBookId?.let { id ->
            bookDetailsViewMode.getBookDetails(id)
        }
    }

    private fun getObservableForRequestedBook() {
        viewLifecycleOwner.lifecycleScope.launch {
            bookDetailsViewMode.selectedBook.collect { selectedBook ->
                setDataToViews(selectedBook)
            }
        }
        /*bookDetailsViewMode.selectedBook.observe(this.requireActivity(), Observer { selectedBook ->
            setDataToViews(selectedBook)
        })*/
    }

    private fun setDataToViews(selectedBook: BooksEntity?) {
        selectedBook?.let { book ->
            book.volumeInfo.imageLinks?.smallThumbnail?.let { bookDetailsBinding?.bookDetailsImage?.loadUrlImage(it.replace("http", "https"), this.requireContext()) }
            bookDetailsBinding?.bookDetailsTitle?.text = book.volumeInfo.title
            bookDetailsBinding?.bookDetailsSubtitle?.text = book.volumeInfo.subtitle
            bookDetailsBinding?.bookRating?.text = book.volumeInfo.averageRating.toString()
            bookDetailsBinding?.bookDescription?.text = book.volumeInfo.description
        }
    }
}