package com.alicia.services

import com.alicia.data.Genre
import com.alicia.model.GenreResponse
import com.alicia.model.PaginatedGenreResponse
import com.alicia.repositories.GenreRepository
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import java.util.UUID
import javax.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.mockito.Mockito

@MicronautTest
class GenreServiceTest {

    @Inject
    lateinit var genreService: GenreService

    @Inject
    lateinit var genreRepository: GenreRepository

    @MockBean(GenreRepository::class)
    fun genreRepository(): GenreRepository = Mockito.mock(GenreRepository::class.java)

    @Test
    fun `WHEN get genre by ID THEN return found genre or null`() {
        val genre = Genre(id = UUID.randomUUID(), name = "sci-fi")
        Mockito.`when`(genreRepository.findFirstById(genre.id!!))
            .thenReturn(genre)
            .thenReturn(null)

        val nonNullGenre = genreService.getGenreById(genre.id!!)
        val nullGenre = genreService.getGenreById(genre.id!!)

        assertEquals(genre, nonNullGenre)
        assertNull(nullGenre)
    }

    @Test
    fun `WHEN get genre by name THEN return found genre or null`() {
        val genre = Genre(id = UUID.randomUUID(), name = "sci-fi")
        Mockito.`when`(genreRepository.findFirstByName(genre.name!!))
            .thenReturn(genre)
            .thenReturn(null)

        val nonNullGenre = genreService.getGenreByName(genre.name!!)
        val nullGenre = genreService.getGenreByName(genre.name!!)

        assertEquals(genre, nonNullGenre)
        assertNull(nullGenre)
    }

    @Test
    fun `WHEN get paginated genres THEN query by params and return response`() {
        val genre = Genre(id = UUID.randomUUID(), name = "sci-fi")
        val genreResponse = genre.toGenreResponse()
        val expectedResultPage = PaginatedGenreResponse(
            listOf(genreResponse), 1, 4, 3, 5,
        )
        val pageable = Pageable.from(5, 4)
        val page = Page.of(listOf(genre), pageable, 10)

        Mockito.`when`(genreRepository.getAllGenres(pageable)).thenReturn(page)

        val resultPage = genreService.getPaginatedGenres(5, 4)

        assertEquals(expectedResultPage, resultPage)
    }
}