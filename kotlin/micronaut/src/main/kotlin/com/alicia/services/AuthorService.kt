package com.alicia.services

import com.alicia.data.Author
import com.alicia.exceptions.AuthorNotFoundException
import com.alicia.model.AddAuthorRequest
import com.alicia.model.AuthorResponse
import java.util.*

interface AuthorService {

    fun addAuthor(request: AddAuthorRequest): AuthorResponse

    @Throws(AuthorNotFoundException::class)
    fun findAuthor(id: UUID): Author
}
