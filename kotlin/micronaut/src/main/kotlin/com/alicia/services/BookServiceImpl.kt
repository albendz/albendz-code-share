package com.alicia.services

import com.alicia.constants.Availability
import com.alicia.constants.BookImportHeader
import com.alicia.data.Author
import com.alicia.data.Book
import com.alicia.data.Copy
import com.alicia.data.Genre
import com.alicia.exceptions.BookNotFoundException
import com.alicia.exceptions.EmptyImportCsvException
import com.alicia.exceptions.FailureToReadImportCsvException
import com.alicia.model.AddBookRequest
import com.alicia.model.BookResponse
import com.alicia.model.BulkUploadResponse
import com.alicia.model.PaginatedBookResponse
import com.alicia.repositories.BookRepository
import com.alicia.repositories.GenreRepository
import io.micronaut.data.model.Pageable
import io.micronaut.http.multipart.CompletedFileUpload
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import org.junit.internal.runners.statements.Fail
import org.slf4j.LoggerFactory
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStreamReader
import java.lang.NumberFormatException
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.jvm.Throws

@Singleton
class BookServiceImpl: BookService {

    private val logger = LoggerFactory.getLogger(BookServiceImpl::class.java)

    @Inject
    lateinit var bookRepository: BookRepository

    @Inject
    lateinit var genreRepository: GenreRepository

    override fun search(availabilities: List<Availability>, pageNumber: Int, itemsPerPage: Int): PaginatedBookResponse {
        val pageable = Pageable.from(pageNumber, itemsPerPage)
        var count: Long = 0L
        if (availabilities.size == 2) {
            count = -1L
        }
        val page = bookRepository.findBooks(pageable, availabilities.map { it.name }, count)

        return PaginatedBookResponse(
                numberOfPages = page.totalPages,
                currentPage = page.pageNumber,
                itemsOnPage = page.numberOfElements,
                totalItems = page.totalSize,
                books = page.content.map { it.toBookResponse() },
        )
    }

    override fun getBook(isbn: String): BookResponse =
        bookRepository.findFirstByIsbn(isbn).let { book ->
            if (book != null) {
                book.toBookResponse()
            } else {
                logger.debug("Book not found with ISBN: $isbn")
                throw BookNotFoundException(isbn)
            }
        }

    override fun addBook(addBookRequest: AddBookRequest): BookResponse {
        val genre = addBookRequest.genre?.let {
            genreRepository.findFirstByName(it)  ?: genreRepository.save(Genre(name = it))
        }

        // TODO: add copies
        return bookRepository.save(addBookRequest.toBook(genre)).let { book ->
            book.isbn?.let { bookRepository.findFirstByIsbn(it)?.toBookResponse() } ?: book.toBookResponse()
        }
    }

    @Throws(EmptyImportCsvException::class, FailureToReadImportCsvException::class)
    override fun bulkUpload(csv: CompletedFileUpload): BulkUploadResponse {
        logger.info("Received file with name: ${csv.filename}")
        val books: MutableList<Book> = mutableListOf()
        val errors: MutableList<String> = mutableListOf()

        if (csv.size == 0L) throw EmptyImportCsvException()

        try {
            InputStreamReader(ByteArrayInputStream(csv.bytes)).use { reader ->
                CSVParser(reader, CSVFormat.EXCEL.withFirstRecordAsHeader()).use { parser ->
                    parser.records.forEachIndexed { index, csvRecord ->
                        if (!validateCsvRecord(csvRecord)) {
                            logger.info("Invalid record at row $index")
                            errors.add("Invalid record at row $index")
                        } else {
                            val book = Book(
                                    title = csvRecord.get(BookImportHeader.TITLE.headerValue),
                                    isbn = csvRecord.get(BookImportHeader.ISBN.headerValue),
                                    publicationDate = Date(csvRecord.get(BookImportHeader.PUBLICATION_DATE.headerValue).toLong()),
                                    genre = Genre(name = csvRecord.get(BookImportHeader.GENRE.headerValue)),
                                    author = Author(
                                            firstName = csvRecord.get(BookImportHeader.AUTHOR_FIRST_NAME.headerValue),
                                            lastName = csvRecord.get(BookImportHeader.AUTHOR_LAST_NAME.headerValue)
                                    ),
                                    copies = List(csvRecord.get(BookImportHeader.COPIES.headerValue).toInt()) {
                                        Copy(status = Availability.AVAILABLE.name)
                                    },
                            )

                            books.add(book)
                        }
                    }
                }
            }

            return BulkUploadResponse(
                    books.map { book ->
                        bookRepository.saveWithAuthorAndGenre(book).toBookResponse()
                    },
                    errors
            )
        } catch (ioe: IOException) {
            logger.error("Failure to read csv ${csv.filename}: ${ioe.message}")
            throw FailureToReadImportCsvException()
        }
    }

    private fun validateCsvRecord(csvRecord: CSVRecord): Boolean {
        val valid =
            csvRecord.isSet(BookImportHeader.TITLE.headerValue) &&
            csvRecord.isSet(BookImportHeader.ISBN.headerValue) &&
            csvRecord.isSet(BookImportHeader.PUBLICATION_DATE.headerValue) &&
            csvRecord.isSet(BookImportHeader.GENRE.headerValue) &&
            csvRecord.isSet(BookImportHeader.AUTHOR_FIRST_NAME.headerValue) &&
            csvRecord.isSet(BookImportHeader.AUTHOR_LAST_NAME.headerValue) &&
            csvRecord.isSet(BookImportHeader.COPIES.headerValue)

        try {
            csvRecord.get(BookImportHeader.PUBLICATION_DATE.headerValue).toLong()
            // TODO: maybe not max int number of copies
            csvRecord.get(BookImportHeader.COPIES.headerValue).toInt()
        } catch (nfe: NumberFormatException) {
            return false
        }

        return valid
    }
}

