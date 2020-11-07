package com.alicia.model

import com.fasterxml.jackson.annotation.JsonInclude
import java.util.*

@JsonInclude(JsonInclude.Include.ALWAYS)
data class BookResponse(
        val author: String?,
        val id: UUID?,
        val title: String?,
        val publicationDate: Long? = null,
        val genre: String? = null,
        val status: String?,
        val isbn: String?,
)