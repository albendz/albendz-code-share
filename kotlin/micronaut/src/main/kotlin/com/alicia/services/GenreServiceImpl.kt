package com.alicia.services

import com.alicia.data.Genre
import com.alicia.exceptions.GenreNotFoundException
import com.alicia.model.PaginatedGenreResponse
import com.alicia.repositories.GenreRepository
import io.micronaut.data.model.Pageable
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GenreServiceImpl : GenreService {

    @Inject
    lateinit var genreRepository: GenreRepository

    @Throws(GenreNotFoundException::class)
    override fun getGenreById(id: UUID): Genre? =
        genreRepository.findFirstById(id)

    @Throws(GenreNotFoundException::class)
    override fun getGenreByName(name: String): Genre? =
        genreRepository.findFirstByName(name)

    override fun getPaginatedGenres(pageNumber: Int, itemsPerPage: Int): PaginatedGenreResponse =
        genreRepository.getAllGenres(
            Pageable.from(pageNumber, itemsPerPage)
        ).let {  page ->
            PaginatedGenreResponse(
                page.content.map { it.toGenreResponse() },
                page.numberOfElements,
                itemsPerPage,
                page.totalPages,
                pageNumber
            )
        }

}
