package com.alicia.fixtures

import com.alicia.model.BookResponse
import com.alicia.model.GenreResponse

object BookFixtures {
    private const val defaultIsbn = "DEFAULT_ISBN"
    val defaultResponse = BookResponse(
        author = "Charles Darwin",
        title = "Origin of Species",
        genre = GenreResponse("Science"),
        isbn = defaultIsbn,
    )
}