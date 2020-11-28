package com.alicia.model

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.ALWAYS)
data class PaginatedBookResponse(
        val numberOfPages: Int,
        val currentPage: Int,
        val itemsOnPage: Int,
        val totalItems: Long,
        val books: List<BookResponse>,
)