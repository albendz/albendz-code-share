package com.alicia.controller

import com.alicia.model.AddAuthorRequest
import com.alicia.model.AuthorResponse
import com.alicia.repositories.AuthorRepository
import com.alicia.services.AuthorService
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.MockitoAnnotations
import java.util.*
import javax.inject.Inject

@MicronautTest
class AuthorControllerTests {

    @Inject
    lateinit var server: EmbeddedServer

    @Inject
    @field:Client("/author")
    lateinit var client: HttpClient

    @Inject
    lateinit var authorService: AuthorService

    @Inject
    lateinit var authorRepository: AuthorRepository

    @Inject
    lateinit var authorController: AuthorController

    @BeforeEach
    fun setup() {
        MockitoAnnotations.initMocks(this)
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

        // Action
        val actualResponse = client.toBlocking()
                .retrieve(request, AuthorResponse::class.java)

        // Verify
        assertEquals(expectedResponse, actualResponse.copy(id = id))
    }

    @Test
    fun `WHEN creating invalid author THEN return 400`() {}

    @Test
    fun `WHEN get existing author THEN return existing author`() {}

    @Test
    fun `WHEN get  non-extant author THEN return 404`() {}
}