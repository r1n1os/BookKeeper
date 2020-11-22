package com.example.bookkeeper.main_flow.books_search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookkeeper.R
import com.example.bookkeeper.database.entities.BookInfoEntity
import com.example.bookkeeper.database.entities.bojos.BooksAndBookDetailsBojo
import com.example.bookkeeper.utils.loadUrlImage
import kotlinx.android.synthetic.main.book_item_layout.view.*

class BookSearchAdapter(val onBookSearchListener: OnBookSearchListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnBookSearchListener{
        fun onBookSelected(bookId: String)
    }

    private val booksList = mutableListOf<BooksAndBookDetailsBojo>()

    fun loadSearchedBooks(bookList: MutableList<BooksAndBookDetailsBojo>){
        this.booksList.clear()
        this.booksList.addAll(bookList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BooksViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.book_item_layout, parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is BooksViewHolder -> holder.onBindData(booksList[position])
        }
    }

    override fun getItemCount() = booksList.size

    inner class BooksViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun onBindData(bookEntity: BooksAndBookDetailsBojo) = with(itemView) {
            setDataToViews(bookEntity.booksInfo)
            this.setOnClickListener {
                onBookSearchListener.onBookSelected(booksList[adapterPosition].book.id)
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
}