package com.alicia.repositories

import com.alicia.data.Author
import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.util.*

@Repository
abstract class AuthorRepository: CrudRepository<Author, UUID> {

    abstract fun findFirstByFirstNameAndLastName(firstName: String?, lastName: String?): Author?

    abstract fun findFirstById(id: UUID): Author?

    /**
     * Accepts an author object with partial data and attempts to find an author with matching partial data.
     * If a UUID identifier is present, it will find an author with that UUID.
     *
     * If no identifiers are sufficient or there is no author, this will return null.
     *
     * Otherwise, it will find an author by first and last name (best effort)
     *
     * @param author - the partial author object
     * @return A single closest match to the author.
     */
    fun findClosestMatchAuthor(author: Author): Author? =
        author.id?.let {
            findFirstById(it)
        } ?: findFirstByFirstNameAndLastName(author.firstName, author.lastName)
}