package com.example.bookkeeper.main_flow.books_search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookkeeper.database.entities.BookInfoEntity
import com.example.bookkeeper.database.entities.bojos.BooksAndBookDetailsBojo
import com.example.bookkeeper.databinding.BookItemLayoutBinding
import com.example.bookkeeper.databinding.EmptyBooksLayoutBinding
import com.example.bookkeeper.databinding.TitleLayoutBinding
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
            EMPTY_VIEW_SECTION.ordinal -> EmptyViewHolder(EmptyBooksLayoutBinding.inflate(LayoutInflater.from(parent.context)))
            TITLE_SECTION.ordinal -> TitleViewHolder(TitleLayoutBinding.inflate(LayoutInflater.from(parent.context)))
            else -> BooksViewHolder(BookItemLayoutBinding.inflate(LayoutInflater.from(parent.context)))
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

    inner class TitleViewHolder(val binding: TitleLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun onBindData(titleValue: String) = with(itemView){
            binding.title.text = titleValue
        }
    }

    inner class BooksViewHolder(val binding: BookItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun onBindData(bookEntity: BooksAndBookDetailsBojo) = with(itemView) {
            setDataToViews(bookEntity.booksInfo)
            this.setOnClickListener {
                onBookSearchListener.onBookSelected(booksList[adapterPosition].bookDetails?.book?.id!!)
            }
        }

        private fun setDataToViews(booksInfo: BookInfoEntity) = with(itemView) {
            if (booksInfo.imageLinks != null) {
                booksInfo.imageLinks!!.smallThumbnail?.replace("http", "https")?.let { binding.bookImage.loadUrlImage(it, context)
                }
            }
            binding.bookTitle.text = booksInfo.title
            binding.bookDescription.text = booksInfo.subtitle
            //bookAuthor.text = booksInfo.authors.toString()
        }
    }

    inner class EmptyViewHolder(val biding: EmptyBooksLayoutBinding): RecyclerView.ViewHolder(biding.root){
        fun onBindData() {
        }
    }
}