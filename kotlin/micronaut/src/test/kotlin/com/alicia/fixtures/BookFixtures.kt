package com.alicia.fixtures

import com.alicia.data.Author
import com.alicia.data.Book
import com.alicia.data.Genre
import com.alicia.model.AddBookRequest
import com.alicia.model.BookResponse
import com.alicia.model.GenreResponse
import java.util.*

object BookFixtures {
    private const val defaultIsbn = "DEFAULT_ISBN"
    private val defaultUuid = UUID.randomUUID()

    val defaultResponse = BookResponse(
        author = "Charles Darwin",
        title = "Origin of Species",
        genre = GenreResponse("Science"),
        isbn = defaultIsbn,
        totalCopies = 0,
        availableCopies = 0,
    )

    val defaultBook = Book(
            author = Author(id = defaultUuid),
            title = "Origin of Species",
            genre = Genre(defaultUuid, "Science"),
            isbn = defaultIsbn,
    )

    val addBookRequest = AddBookRequest(
            authorId = defaultUuid,
            title = "Origin of Species",
            isbn = defaultIsbn,
            genre = "Science",
    )
}