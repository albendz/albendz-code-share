package com.alicia.services

import com.alicia.configuration.BookConfiguration
import com.alicia.constants.Availability
import com.alicia.constants.BookImportHeader
import com.alicia.data.Author
import com.alicia.data.Book
import com.alicia.data.Copy
import com.alicia.data.Genre
import com.alicia.exceptions.AuthorRequiredException
import com.alicia.exceptions.BookNotFoundException
import com.alicia.exceptions.EmptyImportCsvException
import com.alicia.exceptions.FailureToReadImportCsvException
import com.alicia.exceptions.NoCopyAvailableException
import com.alicia.exceptions.NoCopyAvailableForBookException
import com.alicia.model.AddBookRequest
import com.alicia.model.BookResponse
import com.alicia.model.BulkUploadResponse
import com.alicia.model.CheckoutRequest
import com.alicia.model.CopyResponse
import com.alicia.model.LoanResponse
import com.alicia.model.PaginatedBookResponse
import com.alicia.repositories.BookRepository
import com.alicia.repositories.GenreRepository
import io.micronaut.data.model.Pageable
import io.micronaut.http.multipart.CompletedFileUpload
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStreamReader
import java.util.Date
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import org.slf4j.LoggerFactory

@Singleton
class BookServiceImpl : BookService {

    private val logger = LoggerFactory.getLogger(BookServiceImpl::class.java)

    @Inject
    lateinit var bookRepository: BookRepository

    @Inject
    lateinit var memberService: MemberService

    @Inject
    lateinit var bookConfiguration: BookConfiguration

    @Inject
    lateinit var authorService: AuthorService

    override fun search(availabilities: List<Availability>, pageNumber: Int, itemsPerPage: Int): PaginatedBookResponse {
        val pageable = Pageable.from(pageNumber, itemsPerPage)
        var count = 0L
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
        val author = addBookRequest.authorId?.let {
            authorService.findAuthor(addBookRequest.authorId)
        } ?: throw AuthorRequiredException()

        return bookRepository.saveBookAndGenre(addBookRequest.toBook(), author).toBookResponse()
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
                                publicationDate = Date(
                                    csvRecord.get(BookImportHeader.PUBLICATION_DATE.headerValue).toLong()
                                ),
                                genre = Genre(name = csvRecord.get(BookImportHeader.GENRE.headerValue)),
                                author = Author(
                                    firstName = csvRecord.get(BookImportHeader.AUTHOR_FIRST_NAME.headerValue),
                                    lastName = csvRecord.get(BookImportHeader.AUTHOR_LAST_NAME.headerValue)
                                ),
                                // TODO: make sure repeat imports don't add more than 10 copies
                                copies = List(csvRecord.get(BookImportHeader.COPIES.headerValue).toInt()) { // TODO limit copies to 10
                                    Copy(status = Availability.AVAILABLE.name)
                                },
                            )

                            books.add(book)
                        }
                    }
                }
            }

            return BulkUploadResponse(
                books.filter { it.isbn != null }.map { book ->
                    bookRepository.saveWithAuthorAndGenreOrReturnExisting(book).toBookResponse()
                },
                errors
            )
        } catch (ioe: IOException) {
            logger.error("Failure to read csv ${csv.filename}: ${ioe.message}")
            throw FailureToReadImportCsvException()
        }
    }

    override fun checkoutBook(isbn: String, checkoutRequest: CheckoutRequest): LoanResponse =
        memberService.getMember(checkoutRequest.memberId).let { member ->
            try {
                bookRepository.checkoutBook(
                    member,
                    isbn,
                    bookConfiguration.loanDurationDays,
                    checkoutRequest.copyId
                ).toLoanResponse()
            } catch (e: NoCopyAvailableForBookException) {
                throw NoCopyAvailableException(isbn)
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

