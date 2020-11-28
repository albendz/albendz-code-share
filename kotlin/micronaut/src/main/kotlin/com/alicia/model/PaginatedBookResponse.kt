package com.alicia.model

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.ALWAYS)
data class PaginatedBookResponse(
        val numberOfPages: Int,
        val currentPage: Int,
        val itemsPerPage: Int,
        val books: List<BookResponse>,
)