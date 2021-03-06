package com.alicia.controller

import com.alicia.exceptions.GenreNotFoundException
import com.alicia.fixtures.GenreFixtures
import com.alicia.model.GenreResponse
import com.alicia.model.PaginatedGenreResponse
import com.alicia.services.GenreService
import com.nhaarman.mockitokotlin2.verify
import io.micronaut.http.HttpRequest.GET
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import javax.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito

@MicronautTest
class GenreControllerTest {

    @Inject
    @field:Client("/genre")
    lateinit var client: HttpClient

    @Inject
    lateinit var genreService: GenreService

    @Inject
    lateinit var genreController: GenreController

    @MockBean(GenreService::class)
    fun genreService(): GenreService = Mockito.mock(GenreService::class.java)

    @Test
    fun `WHEN get genres with no parameters THEN use default parameters`() {
        val request = GET<PaginatedGenreResponse>("/all")

        Mockito.`when`(genreService.getPaginatedGenres(10, 10))
            .thenReturn(GenreFixtures.defaultPaginatedGenreResponse)

        val response = client.toBlocking().retrieve(request, PaginatedGenreResponse::class.java)

        assertEquals(GenreFixtures.defaultGenreResponse, response)
        // Verify genre service was called with default params
        verify(genreService).getPaginatedGenres(10, 10)
    }

    @Test
    fun `WHEN get genres with non-default parameters THEN override defaults`() {
        val request = GET<PaginatedGenreResponse>("/all?itemsPerPage=4&pageNumber=5")

        Mockito.`when`(genreService.getPaginatedGenres(5, 4))
            .thenReturn(GenreFixtures.defaultPaginatedGenreResponse)

        val response = client.toBlocking().retrieve(request, PaginatedGenreResponse::class.java)

        assertEquals(GenreFixtures.defaultPaginatedGenreResponse, response)
        // Verify genre service was called with overridden params
        verify(genreService).getPaginatedGenres(5, 4)
    }

    @Test
    fun `WHEN get genre by ID with existing genre THEN return genre`() {
        val request = GET<PaginatedGenreResponse>("/id/${GenreFixtures.defaultUuid}")

        Mockito.`when`(genreService.getGenreById(GenreFixtures.defaultUuid))
            .thenReturn(GenreFixtures.defaultGenre)

        val response = client.toBlocking().retrieve(request, GenreResponse::class.java)

        assertEquals(GenreFixtures.defaultGenreResponse, response)
    }

    @Test
    fun `WHEN get genre by ID not found THEN return not found exception`() {
        val request = GET<PaginatedGenreResponse>("/id/${GenreFixtures.defaultUuid}")

        val exception = assertThrows<HttpClientResponseException> {
            client.toBlocking().retrieve(request, GenreResponse::class.java)
        }

        assertEquals(HttpStatus.NOT_FOUND, exception.status)
    }

    @Test
    fun `WHEN get genre by name with existing genre THEN return genre`() {
        val request = GET<PaginatedGenreResponse>("/name/${GenreFixtures.defaultGenreResponse.name}")

        Mockito.`when`(genreService.getGenreByName(GenreFixtures.defaultGenreResponse.name!!))
            .thenReturn(GenreFixtures.defaultGenre)

        val response = client.toBlocking().retrieve(request, GenreResponse::class.java)

        assertEquals(GenreFixtures.defaultGenreResponse, response)
    }

    @Test
    fun `WHEN get genre by name not found THEN return not found exception`() {
        val request = GET<PaginatedGenreResponse>("/name/${GenreFixtures.defaultGenreResponse.name}")

        Mockito.`when`(genreService.getGenreByName(GenreFixtures.defaultGenreResponse.name!!))
            .thenThrow(GenreNotFoundException())

        val exception = assertThrows<HttpClientResponseException> {
            client.toBlocking().retrieve(request, GenreResponse::class.java)
        }

        assertEquals(HttpStatus.NOT_FOUND, exception.status)
    }

    @Test
    fun `WHEN get genre by name with invalid name length THEN return invalid request`() {
        val request = GET<PaginatedGenreResponse>("/name/433591644543433976333")

        Mockito.`when`(genreService.getGenreByName(GenreFixtures.defaultGenreResponse.name!!))
            .thenThrow(GenreNotFoundException())

        val exception = assertThrows<HttpClientResponseException> {
            client.toBlocking().retrieve(request, GenreResponse::class.java)
        }

        assertEquals(HttpStatus.BAD_REQUEST, exception.status)
    }
}