package com.alicia.model

import com.fasterxml.jackson.annotation.JsonInclude
import java.util.UUID

@JsonInclude(JsonInclude.Include.ALWAYS)
data class GenreResponse(
    val id: UUID?,
    val name: String?,
)

@JsonInclude(JsonInclude.Include.ALWAYS)
data class PaginatedGenreResponse(
    val genres: List<GenreResponse>,
    val itemsOnPage: Int,
    val itemsPerPage: Int,
    val numberOfPages: Int,
    val pageNumber: Int,
)
