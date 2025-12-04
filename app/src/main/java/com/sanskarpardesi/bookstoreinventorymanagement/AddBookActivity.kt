package com.sanskarpardesi.bookstoreinventorymanagement

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.sanskarpardesi.bookstoreinventorymanagement.databinding.ActivityAddBookBinding
import com.sanskarpardesi.bookstoreinventorymanagement.model.Book
import com.sanskarpardesi.bookstoreinventorymanagement.model.BookViewModel

class AddBookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBookBinding
    private val bookViewModel: BookViewModel by viewModels()
    private var editingBookId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(top = systemBars.top, bottom = systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.root.findViewById(R.id.actualToolbar))
        editingBookId = intent.getIntExtra("book_id", -1).takeIf { it != -1 }

        supportActionBar?.title = if (editingBookId != null) "Edit Book" else "Add New Book"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.root.findViewById<androidx.appcompat.widget.Toolbar>(R.id.actualToolbar)
            .setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        editingBookId?.let { bookId ->
            bookViewModel.getBookById(bookId).observe(this) { book ->
                book?.let {
                    binding.etTitle.setText(it.title)
                    binding.etAuthor.setText(it.author)
                    binding.etGenre.setText(it.genre)
                    binding.etPrice.setText(it.price.toString())
                    binding.etQuantity.setText(it.quantity.toString())
                    binding.etPublisher.setText(it.publisher)
                    binding.etIsbn.setText(it.isbn)
                }
            }
        }

        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val author = binding.etAuthor.text.toString()
            val genre = binding.etGenre.text.toString()
            val price = binding.etPrice.text.toString().toDoubleOrNull()
            val quantity = binding.etQuantity.text.toString().toIntOrNull()
            val publisher = binding.etPublisher.text.toString()
            val isbn = binding.etIsbn.text.toString()

            if (title.isNotEmpty() && author.isNotEmpty() && genre.isNotEmpty() && price != null && quantity != null) {
                val book = Book(
                    id = editingBookId ?: 0,
                    title = title,
                    author = author,
                    genre = genre,
                    price = price,
                    quantity = quantity,
                    publisher = publisher,
                    isbn = isbn
                )

                if (editingBookId != null) {
                    bookViewModel.update(book)
                    Toast.makeText(this, "Book updated!", Toast.LENGTH_SHORT).show()
                } else {
                    bookViewModel.insert(book)
                    Toast.makeText(this, "Book added!", Toast.LENGTH_SHORT).show()
                }
                finish()
            } else {
                Toast.makeText(this, "Please fill all required fields correctly.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
