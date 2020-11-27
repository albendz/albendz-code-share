package com.alicia.controller

import com.alicia.exceptions.AuthorNotFoundException
import com.alicia.model.AddAuthorRequest
import com.alicia.model.AuthorResponse
import com.alicia.repositories.AuthorRepository
import com.alicia.services.AuthorService
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.http.exceptions.HttpException
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import java.util.*
import javax.inject.Inject

@MicronautTest
class AuthorControllerTest {

    @Inject
    @field:Client("/author")
    lateinit var client: HttpClient

    @Inject
    lateinit var authorService: AuthorService

    @Inject
    lateinit var authorController: AuthorController

    @MockBean(AuthorService::class)
    fun authorService(): AuthorService = mock(AuthorService::class.java)

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `WHEN create author THEN return created author`() {
        // Setup
        val id = UUID.randomUUID()
        val expectedResponse = AuthorResponse(
                firstName = "Charles",
                lastName = "Darwin",
                dob = "1970-01-01Z",
                dod = null,
                biography = null,
                id = id,
        )
        val addAuthorRequest = AddAuthorRequest(
                firstName = "Charles",
                lastName = "Darwin",
                dob = 0L,
        )
        val request = HttpRequest.POST("", addAuthorRequest)

        // Mocks
        Mockito.`when`(authorService.addAuthor(addAuthorRequest)).thenReturn(expectedResponse)

        // Action
        val actualResponse = client.toBlocking()
                .retrieve(request, AuthorResponse::class.java)

        // Verify
        assertEquals(expectedResponse, actualResponse)
    }

    @Test
    fun `WHEN creating invalid author THEN return 400`() {
        val addAuthorRequest = AddAuthorRequest(
                firstName = null,
                lastName = "Darwin",
                dob = 0L,
        )
        val request = HttpRequest.POST("", addAuthorRequest)

        try {
            client.toBlocking()
                    .retrieve(request, AuthorResponse::class.java)
        } catch (e: HttpClientResponseException) {
            assertEquals(HttpStatus.BAD_REQUEST, e.status)
        }
    }

    @Test
    fun `WHEN get existing author THEN return existing author`() {
        val id = UUID.randomUUID()
        val expectedResponse = AuthorResponse(
                firstName = "Charles",
                lastName = "Darwin",
                dob = "1970-01-01Z",
                dod = null,
                biography = null,
                id = id,
        )

        val request = HttpRequest.GET<AuthorResponse>("/$id")
        Mockito.`when`(authorService.findAuthor(id)).thenReturn(expectedResponse)

        // Action
        val actualResponse = client.toBlocking()
                .retrieve(request, AuthorResponse::class.java)

        // Verify
        assertEquals(expectedResponse, actualResponse)
    }

    @Test
    fun `WHEN get non-extant author THEN return 404`() {
        val id = UUID.randomUUID()

        val request = HttpRequest.GET<AuthorResponse>("/$id")
        Mockito.`when`(authorService.findAuthor(id)).thenThrow(AuthorNotFoundException(id))

        try {
            client.toBlocking()
                    .retrieve(request, AuthorResponse::class.java)
        } catch (e: HttpClientResponseException) {
            assertEquals(HttpStatus.NOT_FOUND, e.status)
        }
    }
}