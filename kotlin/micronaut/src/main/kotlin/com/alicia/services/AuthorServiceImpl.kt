package com.alicia.services

import com.alicia.exceptions.AuthorNotFoundException
import com.alicia.model.AddAuthorRequest
import com.alicia.model.AuthorResponse
import com.alicia.repositories.AuthorRepository
import org.slf4j.LoggerFactory
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthorServiceImpl: AuthorService {

    private val logger = LoggerFactory.getLogger(AuthorServiceImpl::class.java)

    @Inject
    lateinit var authorRepository: AuthorRepository

    override fun addAuthor(request: AddAuthorRequest): AuthorResponse =
            // TODO: validation
            authorRepository.save(request.toAuthor()).toAuthorResponse()

    override  fun findAuthor(id: UUID): AuthorResponse {
        val author = authorRepository.findById(id)

        if (author.isPresent) {
            return author.get().toAuthorResponse()
        } else {
            logger.info("Author not found by ID: $id")
            throw AuthorNotFoundException(id)
        }
    }
}