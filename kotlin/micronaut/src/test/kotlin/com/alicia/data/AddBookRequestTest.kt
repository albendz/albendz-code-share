package com.alicia.data

import com.alicia.fixtures.BookFixtures
import com.alicia.model.AddBookRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AddBookRequestTest {

    @Test
    fun `WHEN book request is valid THEN has no errors`() {
        assertEquals(Unit, BookFixtures.addBookRequest.validate())
    }

    @Test
    fun `WHEN book request has all errors THEN return all errors`() {
        assertEquals(
            listOf("Author", "ISBN", "Title", "Desired  copies"),
            AddBookRequest(
                authorId = null,
                title = "",
                isbn = "",
                genre = "Science",
                desiredCopies = -1,
            ).getErrors()
        )
    }

    @Test
    fun `WHEN add copies with request THEN create Book with desired copies`() {
        TODO("Unimplemented")
    }

    @Test
    fun `WHEN desired copies is 0 THEN copies is empty list`() {
        TODO("Unimplemented")
    }
}