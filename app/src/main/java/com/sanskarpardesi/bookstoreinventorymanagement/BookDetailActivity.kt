package com.sanskarpardesi.bookstoreinventorymanagement

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.sanskarpardesi.bookstoreinventorymanagement.databinding.ActivityBookDetailBinding
import com.sanskarpardesi.bookstoreinventorymanagement.model.BookViewModel

class BookDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookDetailBinding
    private val bookViewModel: BookViewModel by viewModels()
    private var bookId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = ActivityBookDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(top = systemBars.top, bottom = systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.root.findViewById(R.id.actualToolbar))
        supportActionBar?.title = "Book Details"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.root.findViewById<androidx.appcompat.widget.Toolbar>(R.id.actualToolbar)
            .setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        bookId = intent.getIntExtra("book_id", -1)
        if (bookId != -1) {
            bookViewModel.getBookById(bookId).observe(this) { book ->
                book?.let {
                    binding.etTitle.text = it.title
                    binding.etAuthor.text = "Author: ${it.author}"
                    binding.etGenre.text = "Genre: ${it.genre}"
                    binding.etPrice.text = "Price: $${it.price}"
                    binding.etQuantity.text = "Quantity: ${it.quantity}"
                    binding.etPublisher.text = "Publisher: ${it.publisher}"
                    binding.etIsbn.text = "ISBN: ${it.isbn}"
                }
            }
        } else {
            Toast.makeText(this, "No book ID provided", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
