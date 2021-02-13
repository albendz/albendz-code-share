package com.alicia.data

import com.alicia.constants.Availability
import com.alicia.fixtures.BookFixtures
import com.alicia.model.AddBookRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class AddBookRequestTest {

    @Test
    fun `WHEN book request is valid THEN has no errors`() {
        assertEquals(Unit, BookFixtures.addBookRequest.validate())
    }

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
        val book = BookFixtures.addBookRequest.toBook(null)

        assertEquals(BookFixtures.addBookRequest.desiredCopies, book.copies.size)
        assertTrue(book.copies.all { it.status == Availability.AVAILABLE.name })
    }

    @Test
    fun `WHEN desired copies is 0 THEN copies is empty list`() {
        val book = BookFixtures.addBookRequest.copy(desiredCopies = 0).toBook(null)

        assertEquals(0, book.copies.size)
    }
}