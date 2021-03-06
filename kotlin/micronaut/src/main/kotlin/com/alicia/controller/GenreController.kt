package com.alicia.controller

import com.alicia.exceptions.GenreNotFoundException
import com.alicia.exceptions.InvalidRequestException
import com.alicia.exceptions.InvalidSearchRequestException
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
        ),
        ApiResponse(
            description = "Bad request if search parameters are invalid",
            responseCode = "400"
        )
    )
    fun getAllGenres(
        @QueryValue(defaultValue = "0") pageNumber: Int,
        @QueryValue(defaultValue = "10") itemsPerPage: Int,
    ): PaginatedGenreResponse {

        if(pageNumber < 0 || itemsPerPage < 1) {
            throw InvalidSearchRequestException()
        }

        return genreService.getPaginatedGenres(pageNumber, itemsPerPage)
    }
    // TODO: check for valid page numbers and numbers per page

    @Get("/id/{id}")
    @ApiResponses(
        ApiResponse(
            description = "Return genre with given ID",
            responseCode = "200"
        ),
        ApiResponse(
            description = "Genre not found by ID",
            responseCode = "404"
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
            responseCode = "404"
        )
    )
    fun getGenreByName(@PathVariable name: String): GenreResponse =
        // Validate name via configuration
    name.takeIf { it.length in 1..20 }?.let {
            genreService.getGenreByName(name)?.toGenreResponse() ?: throw GenreNotFoundException()
        } ?: throw InvalidRequestException(listOf("Name"))
}