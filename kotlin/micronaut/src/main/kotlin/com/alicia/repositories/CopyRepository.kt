package com.alicia.repositories

import com.alicia.data.Copy
import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.util.*

@Repository
abstract class CopyRepository: CrudRepository<Copy, UUID> {

    // SELECT c FROM copy c where c.book_isbn = :isbn
    @Query("SELECT c FROM Copy c JOIN Book b on b = c.book WHERE b.isbn = :isbn AND status = :status")
    abstract fun findFirstByIsbnAndStatus(isbn: String, status: String): Copy?
}