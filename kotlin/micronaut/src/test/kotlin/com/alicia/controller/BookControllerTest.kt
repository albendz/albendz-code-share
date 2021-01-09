package com.alicia.controller

import com.alicia.constants.Availability
import com.alicia.exceptions.EmptyImportCsvException
import com.alicia.exceptions.FailureToReadImportCsvException
import com.alicia.exceptions.MemberNotFoundException
import com.alicia.exceptions.NoCopyAvailableException
import com.alicia.fixtures.BookFixtures
import com.alicia.model.*
import com.alicia.services.BookService
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.http.client.multipart.MultipartBody
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import java.util.UUID
import javax.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@MicronautTest
class BookControllerTest {

    @Inject
    @field:Client("/book")
    lateinit var client: HttpClient

    @Inject
    lateinit var bookService: BookService

    @Inject
    lateinit var bookController: BookController

    @MockBean(BookService::class)
    fun bookService(): BookService = Mockito.mock(BookService::class.java)

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `WHEN search for books with default availability THEN return books`() {
        val expectedResponse = PaginatedBookResponse(
            itemsOnPage = 10,
            currentPage = 0,
            numberOfPages = 100,
            totalItems = 10001,
            books = listOf(BookFixtures.defaultResponse)
        )
        val request = HttpRequest.GET<PaginatedBookResponse>("/search")

        Mockito.`when`(
            bookService.search(
                listOf(Availability.AVAILABLE, Availability.UNAVAILABLE),
                0,
                10,
            )
        ).thenReturn(expectedResponse)

        val response = client.toBlocking()
            .retrieve(request, PaginatedBookResponse::class.java)

        assertEquals(expectedResponse, response)
        verify(bookService).search(
            listOf(Availability.AVAILABLE, Availability.UNAVAILABLE),
            0,
            10,
        )
    }

    @Test
    fun `WHEN search for books with non-default availability THEN return books`() {
        val expectedResponse = PaginatedBookResponse(
            itemsOnPage = 10,
            currentPage = 0,
            numberOfPages = 100,
            totalItems = 10001,
            books = listOf(BookFixtures.defaultResponse)
        )
        val request = HttpRequest.GET<PaginatedBookResponse>(
            "/search?availability=UNAVAILABLE&pageNumber=5&itemsPerPage=6"
        )

        Mockito.`when`(
            bookService.search(
                listOf(Availability.UNAVAILABLE),
                5,
                6,
            )
        ).thenReturn(expectedResponse)

        val response = client.toBlocking()
            .retrieve(request, PaginatedBookResponse::class.java)

        assertEquals(expectedResponse, response)
        verify(bookService).search(
            listOf(Availability.UNAVAILABLE),
            5,
            6,
        )
    }

    @Test
    fun `WHEN create book THEN return created book`() {
        val isbn = "0451529065"
        val id = UUID.randomUUID()
        val expectedResponse = BookFixtures.defaultResponse
        val addBookRequest = AddBookRequest(
            authorId = id,
            title = "Origin of Species",
            isbn = isbn,
            genre = "Science",
        )
        val request = HttpRequest.POST("", addBookRequest)

        Mockito.`when`(bookService.addBook(addBookRequest)).thenReturn(expectedResponse)

        // Action
        val actualResponse = client.toBlocking()
            .retrieve(request, BookResponse::class.java)

        assertEquals(expectedResponse, actualResponse)
    }

    @Test
    fun `WHEN creating invalid book THEN return 400`() {
        val isbn = "0451529065"
        val id = UUID.randomUUID()
        val addBookRequest = AddBookRequest(
            authorId = id,
            title = "",
            isbn = isbn,
            genre = "Science",
        )

        val request = HttpRequest.POST("", addBookRequest)

        try {
            client.toBlocking()
                .retrieve(request, BookResponse::class.java)
            fail<Unit>()
        } catch (e: HttpClientResponseException) {
            assertEquals(HttpStatus.BAD_REQUEST, e.status)
        }
    }

    @Test
    fun `WHEN get existing book THEN return book`() {
        val isbn = "0451529065"
        val expectedResponse = BookFixtures.defaultResponse
        val request = HttpRequest.GET<BookResponse>("/$isbn")

        Mockito.`when`(bookService.getBook(isbn)).thenReturn(expectedResponse)

        val actualResponse = client.toBlocking()
            .retrieve(request, BookResponse::class.java)

        assertEquals(expectedResponse, actualResponse)
    }

    @Test
    fun `WHEN get non-existing book THEN throw not found`() {
        val isbn = "0451529065"
        val request = HttpRequest.GET<BookResponse>("/$isbn")

        try {
            client.toBlocking()
                .retrieve(request, BookResponse::class.java)
            fail<Unit>()
        } catch (e: HttpClientResponseException) {
            assertEquals(HttpStatus.NOT_FOUND, e.status)
        }
    }

    @Test
    fun `WHEN upload CSV successful THEN return bulk upload response`() {
        val fileUpload = MultipartBody.builder()
            .addPart("csv", "csv", "my,csv".toByteArray())
            .build()
        val request = HttpRequest.POST("/upload", fileUpload)
            .body(fileUpload)
            .header("Content-Type", "multipart/form-data")
        val expectedResponse = BulkUploadResponse(
            listOf(),
            listOf()
        )

        Mockito.`when`(bookService.bulkUpload(any())).thenReturn(expectedResponse)

        val response = client.toBlocking()
            .retrieve(request, BulkUploadResponse::class.java)

        assertEquals(expectedResponse, response)
    }

    @Test
    fun `WHEN upload CSV invalid THEN return 400 bad request`() {
        val fileUpload = MultipartBody.builder()
            .addPart("csv", "csv", "my,csv".toByteArray())
            .build()
        val request = HttpRequest.POST("/upload", fileUpload)
            .body(fileUpload)
            .header("Content-Type", "multipart/form-data")

        Mockito.`when`(bookService.bulkUpload(any())).thenThrow(EmptyImportCsvException())

        val exception = assertThrows<HttpClientResponseException> {
            client.toBlocking().retrieve(request, BulkUploadResponse::class.java)
        }

        assertEquals(HttpStatus.BAD_REQUEST.code, exception.status.code)
    }

    @Test
    fun `WHEN upload CSV invalid THEN return 500 server error`() {
        val fileUpload = MultipartBody.builder()
            .addPart("csv", "csv", "my,csv".toByteArray())
            .build()
        val request = HttpRequest.POST("/upload", fileUpload)
            .header("Content-Type", "multipart/form-data")

        Mockito.`when`(bookService.bulkUpload(any())).thenThrow(FailureToReadImportCsvException())

        val exception = assertThrows<HttpClientResponseException> {
            client.toBlocking().retrieve(request, BulkUploadResponse::class.java)
        }

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.code, exception.status.code)
    }

    @Test
    fun `WHEN checkout loan for member and book THEN return loan result as 200`() {
        val uuid = UUID.randomUUID()
        val checkoutRequest = CheckoutRequest(uuid)
        val request = HttpRequest.POST("/ABCEDEFG/checkout", checkoutRequest)
        val expectedResponse = LoanResponse(uuid, uuid, uuid, "2021/01/09", 15)

        Mockito.`when`(bookService.checkoutBook("ABCEDEFG", checkoutRequest)).thenReturn(expectedResponse)

        val actualLoan = client.toBlocking().retrieve(request, LoanResponse::class.java)

        assertEquals(expectedResponse, actualLoan)
    }

    @Test
    fun `WHEN checkout loan for member not found THEN return 404`() {
        val uuid = UUID.randomUUID()
        val checkoutRequest = CheckoutRequest(uuid)
        val request = HttpRequest.POST("/ABCEDEFG/checkout", checkoutRequest)

        Mockito.`when`(bookService.checkoutBook("ABCEDEFG", checkoutRequest))
            .thenThrow(MemberNotFoundException())

        val exception = assertThrows<HttpClientResponseException> {
            client.toBlocking().retrieve(request, LoanResponse::class.java)
        }

        assertEquals(HttpStatus.NOT_FOUND, exception.status)
    }

    @Test
    fun `WHEN checkout loan for copy unavailable THEN 400`() {
        val uuid = UUID.randomUUID()
        val checkoutRequest = CheckoutRequest(uuid)
        val request = HttpRequest.POST("/ABCEDEFG/checkout", checkoutRequest)

        Mockito.`when`(bookService.checkoutBook("ABCEDEFG", checkoutRequest))
            .thenThrow(NoCopyAvailableException("isbn"))

        val exception = assertThrows<HttpClientResponseException> {
            client.toBlocking().retrieve(request, LoanResponse::class.java)
        }

        assertEquals(HttpStatus.BAD_REQUEST, exception.status)
    }

    private fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> uninitialized(): T = null as T
}