package com.alicia.services

import com.alicia.model.AddAuthorRequest
import com.alicia.model.AuthorResponse
import java.util.*

interface AuthorService {

    fun addAuthor(request: AddAuthorRequest): AuthorResponse

    fun findAuthor(id: UUID): AuthorResponse
}
