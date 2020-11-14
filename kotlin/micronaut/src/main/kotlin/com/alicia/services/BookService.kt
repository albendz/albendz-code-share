package com.alicia.services

import com.alicia.data.Genre
import com.alicia.model.AddBookRequest
import com.alicia.model.BookResponse
import com.alicia.repositories.BookRepository
import com.alicia.repositories.GenreRepository
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookService {

    private val logger = LoggerFactory.getLogger(BookService::class.java)

    @Inject
    lateinit var bookRepository: BookRepository

    @Inject
    lateinit var genreRepository: GenreRepository

    fun getBook(isbn: String): BookResponse =
        bookRepository.findFirstByIsbn(isbn).let { book ->
            if (book != null) {
                book.toBookResponse()
            } else {
                logger.debug("Book not found with ISBN: $isbn")
                throw Exception("Book not found") // TODO: fix exceptions
            }
        }

    fun addBook(addBookRequest: AddBookRequest): BookResponse {
        val genre = addBookRequest.genre?.let {
            genreRepository.findFirstByName(it)  ?: genreRepository.save(Genre(name = it))
        }

        val book = bookRepository.save(addBookRequest.toBook(genre))
        return book.toBookResponse()
    }
}

