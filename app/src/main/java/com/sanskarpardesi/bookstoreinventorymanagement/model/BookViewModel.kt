package com.sanskarpardesi.bookstoreinventorymanagement.model

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class BookViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: BookRepository
    val allBooks: LiveData<List<Book>>

    init {
        val dao = BookDatabase.getDatabase(application).bookDao()
        repository = BookRepository(dao)
        allBooks = repository.allBooks
    }

    fun insert(book: Book) = viewModelScope.launch {
        repository.insert(book)
    }

    fun update(book: Book) = viewModelScope.launch {
        repository.update(book)
    }

    fun delete(book: Book) = viewModelScope.launch {
        repository.delete(book)
    }

    fun getBookById(id: Int): LiveData<Book> {
        val result = MutableLiveData<Book>()
        viewModelScope.launch {
            result.value = repository.getBookById(id)
        }
        return result
    }
}
