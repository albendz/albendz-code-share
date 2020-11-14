package com.alicia.repositories

import com.alicia.data.Book
import io.micronaut.data.annotation.Repository
import java.util.*
import javax.inject.Singleton
import io.micronaut.data.repository.CrudRepository

@Repository
abstract class BookRepository: CrudRepository<Book, String> {

    abstract fun findFirstByIsbn(isbn: String): Book?
}