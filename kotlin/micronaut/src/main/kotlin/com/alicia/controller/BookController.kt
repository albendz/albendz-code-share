package com.alicia.controller

import com.alicia.constants.Availability
import com.alicia.exceptions.InvalidSearchRequestException
import com.alicia.model.*
import com.alicia.services.BookService
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.http.multipart.CompletedFileUpload
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import javax.inject.Inject

@Controller("/book")
class BookController {

    @Inject
    lateinit var bookService: BookService

    @Get("/search")
    @ApiResponses(
        ApiResponse(
            description = "Return books from search",
            responseCode = "200"
        ),
        ApiResponse(
            description = "Bad request if search parameters are invalid",
            responseCode = "400"
        )
    )
    fun searchBooks(
        @QueryValue(defaultValue = "AVAILABLE,UNAVAILABLE") availability: List<Availability>,
        @QueryValue(defaultValue = "0") pageNumber: Int,
        @QueryValue(defaultValue = "10") itemsPerPage: Int,
    ): PaginatedBookResponse {

        if(pageNumber < 0 || itemsPerPage < 1) {
            throw InvalidSearchRequestException()
        }

        return bookService.search(availability, pageNumber, itemsPerPage)
    }

    @Post(consumes = ["application/json"], produces = ["application/json"])
    @ApiResponses(
        ApiResponse(
            description = "Create a book",
            responseCode = "201"
        ),
        ApiResponse(
            description = "Invalid book data provided",
            responseCode = "400"
        )
    )
    fun addBook(book: AddBookRequest): BookResponse {
        book.validate()
        return bookService.addBook(book)
    }

    @Get("/{isbn}")
    @ApiResponses(
        ApiResponse(
            description = """Return book with ID.
                The Book ID is a UUID type.
                If a non-UUID is passed, you will receive an error.
            """,
            responseCode = "200"
        ),
        ApiResponse(
            description = "Book with ID not found",
            responseCode = "404"
        )
    )
    fun getBook(@PathVariable isbn: String): BookResponse = bookService.getBook(isbn)

    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Post("/upload")
    @ApiResponses(
        ApiResponse(
            description = "All books attempted to import or record errors",
            responseCode = "201"
        ),
        ApiResponse(
            description = "Invalid book data provided",
            responseCode = "400"
        )
    )
    fun bulkCreate(@Body csv: CompletedFileUpload): BulkUploadResponse = bookService.bulkUpload(csv)

    @Post("/{isbn}/checkout")
    @ApiResponses(
        ApiResponse(
            description = "Loan for book by member is successfully created or updated",
            responseCode = "201"
        ),
        ApiResponse(
            description = "Book has no available copies or copy specified is unavailable",
            responseCode = "400"
        )
    )
    fun checkoutBook(@PathVariable isbn: String, checkoutRequest: CheckoutRequest): LoanResponse =
        checkoutRequest.validate().let {
            bookService.checkoutBook(isbn, checkoutRequest)
        }

    // Check in
    // Place hold
}
