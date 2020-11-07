package com.alicia.services

import com.alicia.data.Book
import com.alicia.model.AddBookRequest
import com.alicia.model.BookResponse
import com.alicia.repositories.BookRepository
import org.slf4j.LoggerFactory
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookService {

    private val logger = LoggerFactory.getLogger(BookService::class.java)

    @Inject
    lateinit var bookRepository: BookRepository

    fun getBook(id: UUID): BookResponse {
        return bookRepository.findById(id).let { book ->
            if (book.isEmpty) {
                logger.info("Book not found with ID $id")
                throw Exception("Book not found") // TODO: not a generic exception
            }
            book.get().toBookResponse()
        }
    }

    fun addBook(addBookRequest: AddBookRequest): BookResponse {
        val book = bookRepository.save(addBookRequest.toBook())
        return book.toBookResponse()
    }
}

