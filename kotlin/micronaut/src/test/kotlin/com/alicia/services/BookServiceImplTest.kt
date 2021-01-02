package com.alicia.services

import com.alicia.fixtures.BookFixtures
import com.alicia.model.PaginatedBookResponse
import com.alicia.repositories.BookRepository
import com.alicia.repositories.GenreRepository
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import javax.inject.Inject
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Disabled

@MicronautTest
class BookServiceImplTest {

    @Inject
    lateinit var bookService: BookServiceImpl

    @Inject
    lateinit var bookRepository: BookRepository

    @Inject
    lateinit var genreRepository: GenreRepository

    @MockBean(BookRepository::class)
    fun bookRepository(): BookRepository = Mockito.mock(BookRepository::class.java)

    @MockBean(GenreRepository::class)
    fun genreRepository(): GenreRepository = Mockito.mock(GenreRepository::class.java)

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `WHEN search for books THEN pass page information to repository `() {
        val expectedPageable =  Pageable.from(5, 10)
        val page = Page.of(
                listOf(BookFixtures.defaultBook),
                expectedPageable,
                100
        )
        val expectedResponse = PaginatedBookResponse(
                numberOfPages = page.totalPages,
                currentPage = page.pageNumber,
                itemsOnPage = page.numberOfElements,
                totalItems = page.totalSize,
                books = page.content.map { it.toBookResponse() },
        )
        Mockito.`when`(bookRepository.findBooks(expectedPageable, emptyList())).thenReturn(page)

        val response = bookService.search(emptyList(), 5, 10)

        Mockito.verify(bookRepository).findBooks(expectedPageable, emptyList(), 0L)
        assertEquals(expectedResponse, response)
    }

    @Test
    fun `WHEN checkout book with valid member and valid loan THEN return loan`() {
        TODO("Unimplemented")
    }

    @Test
    fun `WHEN checkout book with non-existing member THEN throw exception`() {
        TODO("Unimplemented")
    }

    @Test
    fun `WHEN checkout book with valid member and no loan available THEN throw no copy exception`() {
        TODO("Unimplemented")
    }
}