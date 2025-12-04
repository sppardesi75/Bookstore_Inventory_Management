package com.sanskarpardesi.bookstoreinventorymanagement.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.sanskarpardesi.bookstoreinventorymanagement.R
import com.sanskarpardesi.bookstoreinventorymanagement.databinding.ItemBookBinding
import com.sanskarpardesi.bookstoreinventorymanagement.model.Book

class BookAdapter(
    private var bookList: List<Book>,
    private val onView: (Book) -> Unit,
    private val onEdit: (Book) -> Unit,
    private val onDelete: (Book) -> Unit
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    inner class BookViewHolder(val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(book: Book) {
            binding.tvTitle.text = book.title
            binding.tvAuthor.text = "Author: ${book.author}"
            binding.tvGenre.text = "Genre: ${book.genre}"
            binding.tvPrice.text = "Price: $${book.price}"
            binding.tvQuantity.text = "Qty: ${book.quantity}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun getItemCount() = bookList.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = bookList[position]
        holder.bind(book)

        // ✅ Long press to view details
        holder.itemView.setOnLongClickListener {
            onView(book)
            true
        }

        // ✅ 3-dot popup menu
        holder.binding.ivMenu.setOnClickListener {
            val popup = PopupMenu(holder.binding.root.context, holder.binding.ivMenu)
            popup.inflate(R.menu.menu_book_item)
            popup.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_view -> {
                        onView(book)
                        true
                    }
                    R.id.action_edit -> {
                        onEdit(book)
                        true
                    }
                    R.id.action_delete -> {
                        onDelete(book)
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }
    }

    fun updateList(newList: List<Book>) {
        bookList = newList
        notifyDataSetChanged()
    }
}
