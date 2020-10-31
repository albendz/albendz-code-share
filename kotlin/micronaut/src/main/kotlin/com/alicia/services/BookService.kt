package com.alicia.services

import com.alicia.data.Book
import com.alicia.model.AddBookRequest
import com.alicia.model.BookResponse
import com.alicia.repositories.BookRepository
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookService {

    @Inject
    lateinit var bookRepository: BookRepository

    fun getBook(id: UUID): BookResponse {
        return bookRepository.findById(id).let { book ->
            if (book.isEmpty) throw Exception("Book not found") // TODO: not a generic exception
            BookResponse(id = book.get().bookId, title = book.get().title) // TODO: use BookResponse.fromBook()
        }
    }

    fun addBook(addBookRequest: AddBookRequest): BookResponse {
        val book = bookRepository.save(Book(bookId = null, title = addBookRequest.title))
        return  BookResponse(id = book.bookId, title = book.title)
    }
}

