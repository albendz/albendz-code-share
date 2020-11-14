package com.alicia.services

import com.alicia.constants.BookImportHeader
import com.alicia.data.Author
import com.alicia.data.Book
import com.alicia.data.Genre
import com.alicia.model.AddBookRequest
import com.alicia.model.BookResponse
import com.alicia.repositories.BookRepository
import com.alicia.repositories.GenreRepository
import io.micronaut.http.multipart.CompletedFileUpload
import io.micronaut.http.server.multipart.MultipartBody
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.slf4j.LoggerFactory
import java.io.ByteArrayInputStream
import java.io.InputStreamReader
import java.util.*
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

    fun bulkUpload(csv: CompletedFileUpload): List<BookResponse> {
        logger.info("Received file with name: ${csv.filename}")
        val books: MutableList<Book> = mutableListOf()

        InputStreamReader(ByteArrayInputStream(csv.bytes)).use { reader ->
            CSVParser(reader, CSVFormat.EXCEL.withFirstRecordAsHeader()).use { parser ->
                val records = parser.records
                records.forEach {
                    // Validate format of row
                    val book = Book(
                            title = it.get(BookImportHeader.TITLE.headerValue),
                            isbn = it.get(BookImportHeader.ISBN.headerValue),
                            publicationDate = Date(it.get(BookImportHeader.PUBLICATION_DATE.headerValue).toLong()),
                            genre = Genre(name = it.get(BookImportHeader.GENRE.headerValue)),
                            author = Author(
                                    firstName = it.get(BookImportHeader.AUTHOR_FIRST_NAME.headerValue),
                                    lastName = it.get(BookImportHeader.AUTHOR_LAST_NAME.headerValue)
                            ),
                    )

                    books.add(book)
                }
            }
        }

        return books.map { book -> 
            bookRepository.saveWithAuthorAndGenre(book).toBookResponse()
        }
    }
}

