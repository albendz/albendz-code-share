package com.alicia.repositories

import com.alicia.data.Author
import com.alicia.data.Name
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

@Repository
abstract class AuthorRespository: CrudRepository<Author, Name> {
}