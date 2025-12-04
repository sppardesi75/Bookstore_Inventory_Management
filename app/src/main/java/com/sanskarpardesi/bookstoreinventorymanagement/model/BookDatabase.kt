package com.sanskarpardesi.bookstoreinventorymanagement.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [Book::class], version = 2, exportSchema = false)
abstract class BookDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao

    companion object {
        @Volatile
        private var INSTANCE: BookDatabase? = null

        fun getDatabase(context: Context): BookDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BookDatabase::class.java,
                    "book_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            GlobalScope.launch(Dispatchers.IO) {
                                INSTANCE?.bookDao()?.let { dao ->
                                    dao.insertBook(Book(0, "1984", "George Orwell", "Dystopian", 12.99, 5, "Secker & Warburg", "1234567890"))
                                    dao.insertBook(Book(0, "The Hobbit", "J.R.R. Tolkien", "Fantasy", 15.49, 3, "Allen & Unwin", "2345678901"))
                                    dao.insertBook(Book(0, "Atomic Habits", "James Clear", "Self-Help", 18.00, 8, "Penguin", "3456789012"))
                                    dao.insertBook(Book(0, "To Kill a Mockingbird", "Harper Lee", "Classic", 10.99, 4, "J.B. Lippincott & Co.", "4567890123"))
                                    dao.insertBook(Book(0, "The Alchemist", "Paulo Coelho", "Adventure", 13.50, 6, "HarperOne", "5678901234"))
                                    dao.insertBook(Book(0, "Sapiens", "Yuval Noah Harari", "History", 19.99, 7, "Harper", "6789012345"))
                                    dao.insertBook(Book(0, "The Catcher in the Rye", "J.D. Salinger", "Fiction", 9.99, 2, "Little, Brown", "7890123456"))
                                    dao.insertBook(Book(0, "The Great Gatsby", "F. Scott Fitzgerald", "Classic", 11.99, 5, "Scribner", "8901234567"))
                                    dao.insertBook(Book(0, "Rich Dad Poor Dad", "Robert Kiyosaki", "Finance", 14.25, 6, "Warner Books", "9012345678"))
                                    dao.insertBook(Book(0, "The Power of Now", "Eckhart Tolle", "Spirituality", 16.00, 4, "New World Library", "0123456789"))
                                }
                            }
                        }
                    })
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
