package com.sanskarpardesi.bookstoreinventorymanagement

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.MaterialToolbar
import com.sanskarpardesi.bookstoreinventorymanagement.adapter.BookAdapter
import com.sanskarpardesi.bookstoreinventorymanagement.databinding.ActivityMainBinding
import com.sanskarpardesi.bookstoreinventorymanagement.model.BookViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val bookViewModel: BookViewModel by viewModels()
    private lateinit var bookAdapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(top = systemBars.top, bottom = systemBars.bottom)
            insets
        }

        val toolbar = binding.root.findViewById<MaterialToolbar>(R.id.actualToolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Bookstore Inventory"

        bookAdapter = BookAdapter(
            emptyList(),
            onView = { book ->
                startActivity(Intent(this, BookDetailActivity::class.java).apply {
                    putExtra("book_id", book.id)
                })
            },
            onEdit = { book ->
                startActivity(Intent(this, AddBookActivity::class.java).apply {
                    putExtra("book_id", book.id)
                })
            },
            onDelete = { book ->
                bookViewModel.delete(book)
                Toast.makeText(this, "Book deleted", Toast.LENGTH_SHORT).show()
            }
        )

        binding.recyclerViewBooks.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = bookAdapter
        }

        bookViewModel.allBooks.observe(this) { books ->
            bookAdapter.updateList(books)
            binding.tvEmpty.visibility = if (books.isEmpty()) View.VISIBLE else View.GONE
        }

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, AddBookActivity::class.java))
        }
    }
}
