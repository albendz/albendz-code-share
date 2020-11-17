package com.alicia.services

import com.alicia.constants.BookImportHeader
import com.alicia.data.Author
import com.alicia.data.Book
import com.alicia.data.Genre
import com.alicia.exceptions.BookNotFoundException
import com.alicia.exceptions.EmptyImportCsvException
import com.alicia.exceptions.FailureToReadImportCsvException
import com.alicia.model.AddBookRequest
import com.alicia.model.BookResponse
import com.alicia.model.BulkUploadResponse
import com.alicia.repositories.BookRepository
import com.alicia.repositories.GenreRepository
import io.micronaut.http.multipart.CompletedFileUpload
import io.micronaut.http.server.multipart.MultipartBody
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import org.slf4j.LoggerFactory
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStreamReader
import java.lang.NumberFormatException
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
                throw BookNotFoundException(isbn)
            }
        }

    fun addBook(addBookRequest: AddBookRequest): BookResponse {
        val genre = addBookRequest.genre?.let {
            genreRepository.findFirstByName(it)  ?: genreRepository.save(Genre(name = it))
        }

        return bookRepository.save(addBookRequest.toBook(genre)).toBookResponse()
    }

    fun bulkUpload(csv: CompletedFileUpload): BulkUploadResponse {
        logger.info("Received file with name: ${csv.filename}")
        val books: MutableList<Book> = mutableListOf()
        val errors: MutableList<String> = mutableListOf()

        if (csv.size == 0L) throw EmptyImportCsvException()

        try {
            InputStreamReader(ByteArrayInputStream(csv.bytes)).use { reader ->
                CSVParser(reader, CSVFormat.EXCEL.withFirstRecordAsHeader()).use { parser ->
                    val records = parser.records
                    for (index in 0 until records.size) {
                        val csvRecord = records[index]

                        if (!validateCsvRecord(csvRecord)) {
                            logger.info("Invalid record at row $index")
                            errors.add("Invalid record at row $index")
                            continue
                        }
                        val book = Book(
                                title = csvRecord.get(BookImportHeader.TITLE.headerValue),
                                isbn = csvRecord.get(BookImportHeader.ISBN.headerValue),
                                publicationDate = Date(csvRecord.get(BookImportHeader.PUBLICATION_DATE.headerValue).toLong()),
                                genre = Genre(name = csvRecord.get(BookImportHeader.GENRE.headerValue)),
                                author = Author(
                                        firstName = csvRecord.get(BookImportHeader.AUTHOR_FIRST_NAME.headerValue),
                                        lastName = csvRecord.get(BookImportHeader.AUTHOR_LAST_NAME.headerValue)
                                ),
                        )

                        books.add(book)
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
        csvRecord.isSet(BookImportHeader.AUTHOR_LAST_NAME.headerValue)

        try {
            csvRecord.get(BookImportHeader.PUBLICATION_DATE.headerValue).toLong()
        } catch (nfe: NumberFormatException) {
            return false
        }

        return valid
    }
}

