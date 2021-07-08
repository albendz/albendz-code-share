package com.alicia.repositories

import com.alicia.data.Copy
import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.util.*

@Repository
abstract class CopyRepository: CrudRepository<Copy, UUID> {

    @Query("SELECT c FROM Copy c JOIN Book b ON b = c.book WHERE b.isbn = :isbn AND status = :status")
    abstract fun findFirstByIsbnAndStatus(isbn: String, status: String): Copy?

    @Query("SELECT c FROM Copy c  WHERE c.book.isbn = :isbn")
    abstract fun findAllByIsbn(isbn: String): List<Copy>

    @Query("SELECT c FROM Copy c JOIN Book b ON b = c.book WHERE b.isbn = :isbn AND c.copyId = :copyId")
    abstract fun findFirstByCopyIdAndIsbn(copyId: UUID, isbn: String): Copy?
}
