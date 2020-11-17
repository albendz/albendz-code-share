package com.alicia.model

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.ALWAYS)
data class BulkUploadResponse(
        val books: List<BookResponse>,
        val errors: List<String>,
)
