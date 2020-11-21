package com.example.bookkeeper.main_flow.books_search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookkeeper.R
import com.example.bookkeeper.database.entities.BookInfoEntity
import com.example.bookkeeper.database.entities.bojos.BooksAndBookDetailsBojo
import kotlinx.android.synthetic.main.book_item_layout.view.*

class BookSearchAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
        }

        private fun setDataToViews(booksInfo: BookInfoEntity) = with(itemView) {
            //bookImage.loadUrlImage(booksInfo.)
            bookTitle.text = booksInfo.title
            bookDescription.text = booksInfo.subtitle
        }
    }
}