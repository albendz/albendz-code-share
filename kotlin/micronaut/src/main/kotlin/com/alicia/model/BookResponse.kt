package com.alicia.model

import com.fasterxml.jackson.annotation.JsonInclude
import java.util.*

@JsonInclude(JsonInclude.Include.ALWAYS)
data class BookResponse(
        val author: String? = null,
        val id: UUID?,
        val title: String?,
)