package com.alicia.controller

import com.alicia.model.AddAuthorRequest
import com.alicia.model.AuthorResponse
import com.alicia.services.AuthorService
import io.micronaut.http.annotation.*
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import java.util.*
import javax.inject.Inject

@Controller("/author")
class AuthorController {

    @Inject
    lateinit var authorService: AuthorService

    @Post(consumes = ["application/json"], produces = ["application/json"])
    @ApiResponses(
            ApiResponse(
                    description = "Create an author",
                    responseCode = "201"
            ),
            ApiResponse(
                    description = "Invalid author data provided",
                    responseCode = "400"
            )
    )
    fun addAuthor(addAuthorRequest: AddAuthorRequest): AuthorResponse {
        addAuthorRequest.validate()
        return authorService.addAuthor(addAuthorRequest)
    }

    @Get("/{id}")
    @ApiResponses(
            ApiResponse(
                    description = "Return author with ID",
                    responseCode = "200"
            ),
            ApiResponse(
                    description = "Author with ID not found",
                    responseCode = "404"
            )
    )
    fun getAuthor(@PathVariable id: UUID): AuthorResponse = authorService.findAuthor(id).toAuthorResponse()
}
