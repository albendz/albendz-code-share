package com.alicia.services

import com.alicia.data.Genre
import com.alicia.model.PaginatedGenreResponse
import java.util.UUID

interface GenreService {

    fun getGenreById(id: UUID): Genre?

    fun getGenreByName(name: String): Genre?

    fun getPaginatedGenres(pageNumber: Int, itemsPerPage: Int): PaginatedGenreResponse

}
