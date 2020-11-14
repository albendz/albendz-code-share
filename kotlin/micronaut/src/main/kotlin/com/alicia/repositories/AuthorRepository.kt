package com.alicia.repositories

import com.alicia.data.Author
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.util.*

@Repository
abstract class AuthorRepository: CrudRepository<Author, UUID> {
}