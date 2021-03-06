package com.alicia.services

import com.alicia.data.Genre
import com.alicia.exceptions.GenreNotFoundException
import com.alicia.model.PaginatedGenreResponse
import java.util.UUID
interface GenreService {

    @Throws(GenreNotFoundException::class)
    fun getGenreById(id: UUID): Genre?

    @Throws(GenreNotFoundException::class)
    fun getGenreByName(name: String): Genre?

    fun getPaginatedGenres(pageNumber: Int, itemsPerPage: Int): PaginatedGenreResponse

}

