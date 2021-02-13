package com.alicia.controller

import com.alicia.exceptions.GenreNotFoundException
import com.alicia.exceptions.InvalidRequestException
import com.alicia.model.GenreResponse
import com.alicia.model.PaginatedGenreResponse
import com.alicia.services.GenreService
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.QueryValue
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import java.util.UUID
import javax.inject.Inject

@Controller("/genre")
class GenreController {

    @Inject
    lateinit var genreService: GenreService

    @Get("/all")
    @ApiResponses(
        ApiResponse(
            description = "Return all genres paginated",
            responseCode = "200"
        )
    )
    fun getAllGenres(
        @QueryValue(defaultValue = "0") pageNumber: Int,
        @QueryValue(defaultValue = "10") itemsPerPage: Int,
    ): PaginatedGenreResponse = genreService.getPaginatedGenres(pageNumber, itemsPerPage)

    @Get("/id/{id}")
    @ApiResponses(
        ApiResponse(
            description = "Return genre with given ID",
            responseCode = "200"
        ),
        ApiResponse(
            description = "Genre not found by ID",
            responseCode = "400"
        )
    )
    fun getGenreById(@PathVariable id: UUID): GenreResponse =
        genreService.getGenreById(id)?.toGenreResponse() ?: throw GenreNotFoundException()

    @Get("/name/{name}")
    @ApiResponses(
        ApiResponse(
            description = "Return genre with given name",
            responseCode = "200"
        ),
        ApiResponse(
            description = "Genre not found by name",
            responseCode = "400"
        )
    )
    fun getGenreByName(@PathVariable name: String): GenreResponse =
        // Validate name via configuration
        name.takeIf { it.length <= 100 }?.let {
            genreService.getGenreByName(name)?.toGenreResponse() ?: throw GenreNotFoundException()
        } ?: throw InvalidRequestException(listOf("Name"))
}