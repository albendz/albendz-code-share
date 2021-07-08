package com.alicia.model

data class CopiesResponse(
    val copies: List<CopyResponse>,
    val numberOfCopies: Int
)