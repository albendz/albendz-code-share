package com.alicia.controller

import com.alicia.constants.Availabilities
import com.alicia.model.AddBookRequest
import com.alicia.model.BookResponse
import com.alicia.services.BookService
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.http.multipart.CompletedFileUpload
import io.micronaut.http.server.multipart.MultipartBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import java.util.*
import javax.inject.Inject

@Controller("/book")
class BookController {

    @Inject
    lateinit var bookService: BookService

    @Get
    @ApiResponses(
            ApiResponse(
                    description = "Return books from search",
                    responseCode = "200"
            )
    )
    fun searchBooks(
            @QueryValue availability: Availabilities
    ): String = "Unimplemented: $availability"

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
    fun addBook(book: AddBookRequest): BookResponse = bookService.addBook(book)

    @Get("/{isbn}")
    @ApiResponses(
            ApiResponse(
                    description = "Return book with ID",
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
                    description = "Created all books",
                    responseCode = "201"
            ),
            ApiResponse(
                    description = "Invalid book data provided",
                    responseCode = "400"
            )
    )
    fun bulkCreate(csv: CompletedFileUpload): List<BookResponse> = bookService.bulkUpload(csv)


    // Search
    //  Get book info
    // Checkout
    // Checkin
    // Place hold
}