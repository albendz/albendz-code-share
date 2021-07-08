package com.alicia.controller

import com.alicia.model.AddAuthorRequest
import com.alicia.model.AddBookRequest
import com.alicia.model.AuthorResponse
import com.alicia.model.BookResponse
import com.alicia.model.BulkUploadResponse
import com.alicia.model.GenreResponse
import com.alicia.model.PaginatedBookResponse
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.multipart.MultipartBody
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import java.io.File
import java.util.UUID
import javax.inject.Inject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

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

    @Test
    fun `WHEN upload CSV successful THEN return bulk upload response`() {
        val defaultUuid = UUID.randomUUID()
        val bookA = BookResponse(
            author = "Noble, Safiya Umoja",
            availableCopies = 3,
            genre = GenreResponse(id = defaultUuid, name = "nonfiction"),
            isbn = "ABCEDEFG",
            title = "Algorithms of Oppression",
            totalCopies = 3,
        )
        val bookB = BookResponse(
            author = "Griffiths, Dawn",
            availableCopies = 8,
            genre = GenreResponse(id = defaultUuid, name = "science"),
            isbn = "123ABC456GEF",
            title = "Head First Kotlin",
            totalCopies = 8,
        )
        val fileUpload = MultipartBody.builder()
            .addPart(
                "csv",
                File("C:\\Users\\alici\\repos\\albendz-code-share\\kotlin\\micronaut\\src\\test\\resources\\good_import.csv")
            )
            .build()
        val request = HttpRequest.POST("/upload", fileUpload)
            .body(fileUpload)
            .header("Content-Type", "multipart/form-data")
        val expectedResponse = BulkUploadResponse(
            listOf(bookA, bookB),
            listOf()
        )

        val response = bookClient.toBlocking()
            .retrieve(request, BulkUploadResponse::class.java)

        // Change UUIDs for equality
        response.books.forEach {
            it.genre?.id = defaultUuid
        }

        Assertions.assertEquals(expectedResponse, response)

        // PART 2: Get the created books
        val actualBookA = getExistingBook(bookA.isbn!!)
        val actualbookB = getExistingBook(bookB.isbn!!)

        Assertions.assertEquals(bookA, actualBookA.apply { genre!!.id = defaultUuid })
        Assertions.assertEquals(bookB, actualbookB.apply { genre!!.id = defaultUuid })
    }


    @Test
    fun `WHEN bulk upload csv with no books THEN throw empty import exception`() {

    }

    @Test
    fun `WHEN bulk upload csv for non-csv file THEN throw exception`() {

    }

    @Test
    fun `WHEN bulk upload csv where csv has an error of each type THEN do not save and return errors`() {

    }

    @Test
    fun `WHEN bulk upload csv without header line THEN throw invalid csv`() {

    }

    private fun getExistingBook(isbn: String) =
        HttpRequest.GET<BookResponse>("/$isbn").let {
            bookClient.toBlocking()
                .retrieve(it, BookResponse::class.java)
        }

}