package com.alicia.fixtures

import com.alicia.data.Genre
import com.alicia.model.GenreResponse
import com.alicia.model.PaginatedGenreResponse
import java.util.UUID

object GenreFixtures {
    val defaultUuid = UUID.randomUUID()
    val defaultGenreResponse = GenreResponse(defaultUuid, "sci-fi")
    val defaultPaginatedGenreResponse =
        PaginatedGenreResponse(
            genres = listOf(defaultGenreResponse),
            itemsOnPage = 1,
            itemsPerPage = 10,
            numberOfPages = 1,
            pageNumber = 0,
        )
    val defaultGenre  = Genre(id = defaultUuid, name = "sci-fi")
}