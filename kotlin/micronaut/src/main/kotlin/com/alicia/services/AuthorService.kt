package com.alicia.services

import com.alicia.model.AddAuthorRequest
import com.alicia.model.AuthorResponse
import com.alicia.repositories.AuthorRepository
import org.slf4j.LoggerFactory
import java.lang.Exception
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthorService {

    private val logger = LoggerFactory.getLogger(AuthorService::class.java)

    @Inject
    lateinit var authorRepository: AuthorRepository

    fun addAuthor(request: AddAuthorRequest): AuthorResponse =
            authorRepository.save(request.toAuthor()).toAuthorResponse()

    fun findAuthor(id: UUID): AuthorResponse {
        val author = authorRepository.findById(id)

        if (author.isPresent) {
            return author.get().toAuthorResponse()
        } else {
            logger.info("Author not found by ID: $id")
            throw Exception("Author not found")
        }
    }
}