package com.alicia.data

import com.alicia.fixtures.BookFixtures
import com.alicia.model.AddBookRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BookTest {

    @Test
    fun `WHEN book request is valid THEN has no errors`() {
        assertEquals(Unit, BookFixtures.addBookRequest.validate())
    }

    @Test
    fun `WHEN book request has all errors THEN return all errors`() {
        assertEquals(listOf("Author ID", "ISBN", "Title"),
            AddBookRequest(
                authorId = null,
                title = "",
                isbn = "",
                genre = "Science",
            ).getErrors()
        )
    }
}