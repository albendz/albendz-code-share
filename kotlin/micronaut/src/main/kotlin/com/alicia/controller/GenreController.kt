package com.alicia.controller

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.QueryValue
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import java.util.UUID

@Controller("/genre")
class GenreController {

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
    ): String = TODO("Unimplemented")

    @Get("/{id}")
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
    fun getGenre(@PathVariable id: UUID): String = TODO("Unimplemented")
}