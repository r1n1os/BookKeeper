package com.example.bookkeeper.main_flow.books_search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookkeeper.R
import com.example.bookkeeper.database.entities.BookInfoEntity
import com.example.bookkeeper.database.entities.bojos.BooksAndBookDetailsBojo
import com.example.bookkeeper.main_flow.books_search.model.SearchedBooksModel
import com.example.bookkeeper.main_flow.books_search.model.SearchedBooksModel.BooksViewType.EMPTY_VIEW_SECTION
import com.example.bookkeeper.main_flow.books_search.model.SearchedBooksModel.BooksViewType.TITLE_SECTION
import com.example.bookkeeper.utils.loadUrlImage


class BookSearchAdapter(val onBookSearchListener: OnBookSearchListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnBookSearchListener{
        fun onBookSelected(bookId: String)
    }

    private val booksList = mutableListOf<SearchedBooksModel>()

    fun loadSearchedBooks(bookList: MutableList<SearchedBooksModel>){
        this.booksList.clear()
        this.booksList.addAll(bookList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            EMPTY_VIEW_SECTION.ordinal -> EmptyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.empty_books_layout, parent, false))
            TITLE_SECTION.ordinal -> TitleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.title_layout, parent, false))
            else -> BooksViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.book_item_layout, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is EmptyViewHolder -> holder.onBindData()
            is TitleViewHolder -> holder.onBindData(booksList[position].title)
            is BooksViewHolder -> holder.onBindData(booksList[position].bookDetails!!)
        }
    }

    override fun getItemViewType(position: Int) = booksList[position].booksViewType.ordinal
    override fun getItemCount() = booksList.size

    inner class TitleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var title = itemView.findViewById<TextView>(R.id.title)
        fun onBindData(titleValue: String) = with(itemView){
            title.text = titleValue
        }
    }

    inner class BooksViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var bookTitle = itemView.findViewById<TextView>(R.id.bookTitle)
        var bookDescription = itemView.findViewById<TextView>(R.id.bookDescription)
        var bookImage = itemView.findViewById<ImageView>(R.id.bookImage)
        fun onBindData(bookEntity: BooksAndBookDetailsBojo) = with(itemView) {
            setDataToViews(bookEntity.booksInfo)
            this.setOnClickListener {
                onBookSearchListener.onBookSelected(booksList[adapterPosition].bookDetails?.book?.id!!)
            }
        }

        private fun setDataToViews(booksInfo: BookInfoEntity) = with(itemView) {
            if (booksInfo.imageLinks != null) {
                booksInfo.imageLinks!!.smallThumbnail?.replace("http", "https")?.let { bookImage.loadUrlImage(it, context)
                }
            }
            bookTitle.text = booksInfo.title
            bookDescription.text = booksInfo.subtitle
            //bookAuthor.text = booksInfo.authors.toString()
        }
    }

    inner class EmptyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun onBindData() {
        }
    }
}