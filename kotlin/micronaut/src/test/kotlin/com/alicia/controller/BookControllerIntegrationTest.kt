package com.alicia.controller

import com.alicia.model.*
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.http.client.multipart.MultipartBody
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.util.*
import javax.inject.Inject

@MicronautTest
class BookControllerIntegrationTest {

    @Inject
    @field:Client("/book")
    lateinit var client: HttpClient

    @Disabled
    @Test
    fun `WHEN search for books with default availability THEN return books`() {
        val addAuthorRequest = AddAuthorRequest(
                firstName = "Charles",
                lastName = "Darwin",
                dob = 0L,
        )
        val authorRequest = HttpRequest.POST("", addAuthorRequest)
        val authorResponse = client.toBlocking()
                .retrieve(authorRequest, AuthorResponse::class.java)

        val isbn = "0451529065"
        val addBookRequest = AddBookRequest(
                authorId = authorResponse.id,
                title = "Origin of Species",
                isbn = isbn,
                genre = "Science",
                publicationDate = null,
        )
        val createRequest = HttpRequest.POST("", addBookRequest)

        // Create book
        client.toBlocking()
                .retrieve(createRequest, BookResponse::class.java)

        val expectedResponse = PaginatedBookResponse(
                itemsOnPage = 10,
                currentPage = 0,
                numberOfPages = 100,
                totalItems = 10001,
                books = listOf(
                        BookResponse(
                                author = "Charles Darwin",
                                title = "Origin of Species",
                                genre = GenreResponse("Science"),
                                isbn = isbn,
                        )
                )
        )
        val request = HttpRequest.GET<PaginatedBookResponse>("/search")

        val response = client.toBlocking()
                .retrieve(request, PaginatedBookResponse::class.java)

        Assertions.assertEquals(expectedResponse, response)
    }

    @Disabled
    @Test
    fun `WHEN search for books with non-default availability THEN return books`() {
        val isbn = "0451529065"
        val expectedResponse = PaginatedBookResponse(
                itemsOnPage = 10,
                currentPage = 0,
                numberOfPages = 100,
                totalItems = 10001,
                books = listOf(
                        BookResponse(
                                author = "Charles Darwin",
                                title = "Origin of Species",
                                genre = GenreResponse("Science"),
                                isbn = isbn,
                        )
                )
        )
        val request = HttpRequest.GET<PaginatedBookResponse>(
                "/search?availability=UNAVAILABLE&pageNumber=5&itemsPerPage=6"
        )

        val response = client.toBlocking()
                .retrieve(request, PaginatedBookResponse::class.java)
    }

    @Disabled
    @Test
    fun `WHEN create book THEN return created book`() {
        val isbn = "0451529065"
        val id = UUID.randomUUID()
        val expectedResponse = BookResponse(
                author = "Charles Darwin",
                title = "Origin of Species",
                genre = GenreResponse("Science"),
                isbn = isbn,
        )
        val addBookRequest = AddBookRequest(
                authorId = id,
                title = "Origin of Species",
                isbn = isbn,
                genre = "Science",
        )
        val request = HttpRequest.POST("", addBookRequest)

        // Action
        val actualResponse = client.toBlocking()
                .retrieve(request, BookResponse::class.java)

        Assertions.assertEquals(expectedResponse, actualResponse)
    }

    @Disabled
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
            Assertions.fail<Unit>()
        } catch (e: HttpClientResponseException) {
            Assertions.assertEquals(HttpStatus.BAD_REQUEST, e.status)
        }
    }

    @Disabled
    @Test
    fun `WHEN get existing book THEN return book`(){
        val isbn = "0451529065"
        val expectedResponse = BookResponse(
                author = "Charles Darwin",
                title = "Origin of Species",
                genre = GenreResponse("Science"),
                isbn = isbn,
        )
        val request = HttpRequest.GET<BookResponse>("/$isbn")

        val actualResponse = client.toBlocking()
                .retrieve(request, BookResponse::class.java)

        Assertions.assertEquals(expectedResponse, actualResponse)
    }

    @Disabled
    @Test
    fun `WHEN get non-existing book THEN throw not found`() {
        val isbn = "0451529065"
        val request = HttpRequest.GET<BookResponse>("/$isbn")

        try {
            client.toBlocking()
                    .retrieve(request, BookResponse::class.java)
            Assertions.fail<Unit>()
        } catch (e: HttpClientResponseException) {
            Assertions.assertEquals(HttpStatus.NOT_FOUND, e.status)
        }
    }

    @Disabled
    @Test
    fun `WHEN upload CSV successful THEN return bulk upload response`() {
        val fileUpload = MultipartBody.builder()
                .addPart("csv", "test-data")
                .build()
        val request = HttpRequest.POST("/upload", fileUpload)
                .body(fileUpload)
                .header("Content-Type", "multipart/form-data")
        val expectedResponse = BulkUploadResponse(
                listOf(),
                listOf()
        )

        val response = client.toBlocking()
                .retrieve(request, BulkUploadResponse::class.java)

        Assertions.assertEquals(expectedResponse, response)
    }
}