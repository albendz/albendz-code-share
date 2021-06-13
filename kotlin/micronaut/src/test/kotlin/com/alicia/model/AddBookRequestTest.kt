package com.alicia.model

import com.alicia.constants.Availability
import com.alicia.data.Genre
import com.alicia.fixtures.BookFixtures
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class AddBookRequestTest {

    @Test
    fun `WHEN book request is valid THEN has no errors`() {
        assertEquals(Unit, BookFixtures.addBookRequest.validate())
    }

    @Test
    fun `WHEN toBook with genre on request THEN add genre to book`() =
        assertEquals(Genre(name = "Science"), BookFixtures.addBookRequest.toBook().genre)

    @Test
    fun `WHEN toBook without genre on request THEN set genre to null`() =
        assertNull(BookFixtures.addBookRequest.copy(genre = null).toBook().genre)

    @Test
    fun `WHEN book request has all errors THEN return all errors`() {
        assertEquals(
            listOf("Genre", "Author", "ISBN", "Title", "Desired Copies"),
            AddBookRequest(
                authorId = null,
                title = "",
                isbn = "",
                genre = "",
                desiredCopies = -1,
            ).getErrors()
        )
    }

    @Test
    fun `WHEN add copies with request THEN create Book with desired copies`() {
        val book = BookFixtures.addBookRequest.toBook()

        assertEquals(BookFixtures.addBookRequest.desiredCopies, book.copies.size)
        assertTrue(book.copies.all { it.status == Availability.AVAILABLE.name && it.book == book})
    }

    @Test
    fun `WHEN desired copies is 0 THEN copies is empty list`() {
        val book = BookFixtures.addBookRequest.copy(desiredCopies = 0).toBook()

        assertEquals(0, book.copies.size)
    }
}