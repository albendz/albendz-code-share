package com.alicia.controller

import com.alicia.fixtures.BookFixtures
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
    lateinit var bookClient: HttpClient

    @Inject
    @field:Client("/author")
    lateinit var authorClient: HttpClient

    @Test
    fun `GIVEN an author and a book WHEN search for books THEN return created book`() {
        val addAuthorRequest = AddAuthorRequest(
                firstName = "Charles",
                lastName = "Darwin",
                dob = 0L,
        )
        val authorRequest = HttpRequest.POST("", addAuthorRequest)
        val authorResponse = authorClient.toBlocking()
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
        val book = bookClient.toBlocking()
                .retrieve(createRequest, BookResponse::class.java)

        val expectedResponse = PaginatedBookResponse(
                itemsOnPage = 1,
                currentPage = 0,
                numberOfPages = 1,
                totalItems = 1,
                books = listOf(book)
        )
        val request = HttpRequest.GET<PaginatedBookResponse>("/search")

        val response = bookClient.toBlocking()
                .retrieve(request, PaginatedBookResponse::class.java)

        Assertions.assertEquals(expectedResponse, response)
    }

    @Disabled
    @Test
    fun `WHEN get existing book THEN return book`(){
        val isbn = "0451529065"
        val expectedResponse = BookFixtures.defaultResponse
        val request = HttpRequest.GET<BookResponse>("/$isbn")

        val actualResponse = bookClient.toBlocking()
                .retrieve(request, BookResponse::class.java)

        Assertions.assertEquals(expectedResponse, actualResponse)
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

        val response = bookClient.toBlocking()
                .retrieve(request, BulkUploadResponse::class.java)

        Assertions.assertEquals(expectedResponse, response)
    }
}