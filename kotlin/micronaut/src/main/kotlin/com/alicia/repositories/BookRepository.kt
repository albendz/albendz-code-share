package com.alicia.repositories

import com.alicia.constants.Availability
import com.alicia.data.Book
import com.alicia.data.Copy
import com.alicia.data.Loan
import com.alicia.data.Member
import com.alicia.exceptions.NoCopyAvailableForBookException
import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.data.repository.CrudRepository
import io.micronaut.data.repository.PageableRepository
import java.util.Date
import java.util.UUID
import javax.inject.Inject
import javax.transaction.Transactional

@Repository
abstract class BookRepository : CrudRepository<Book, String>, PageableRepository<Book, String> {

    @Inject
    lateinit var authorRepository: AuthorRepository

    @Inject
    lateinit var genreRepository: GenreRepository

    @Inject
    lateinit var copyRepository: CopyRepository

    @Inject
    lateinit var loanRepository: LoanRepository

    abstract fun findFirstByIsbn(isbn: String): Book?

    abstract fun existsByIsbn(isbn: String?): Boolean

    @Transactional
    fun saveWithAuthorAndGenre(book: Book): Book {
        if (existsByIsbn(book.isbn)) return book

        val author = book.author?.let { author ->
            authorRepository.findFirstByFirstNameAndLastName(author.firstName, author.lastName)
                ?: authorRepository.save(author)
        }

        val genre = book.genre?.let { genre ->
            genreRepository.findFirstByName(book.genre?.name) ?: genreRepository.save(genre)
        }

        book.author = author
        book.genre = genre

        save(book)

        book.copies = book.copies.map { copy ->
            copy.book = book
            copyRepository.save(copy)
        }

        return book
    }

    @Query(
        value = "SELECT b FROM Book b WHERE (SELECT count(*) FROM Copy c " +
                "WHERE c.book = b AND status in :status) > :count",
        countQuery = "SELECT count(b) FROM Book b " +
                "WHERE (SELECT count(*) FROM Copy c WHERE c.book = b AND status in :status) > :count"
    )
    abstract fun findBooks(pageable: Pageable, status: List<String> = listOf("AVAILABLE"), count: Long = 0): Page<Book>

    @Transactional
    fun checkoutBook(member: Member, isbn: String, lengthDays: Int, copyId: UUID? = null): Loan =
    // 1. get available copies of book, checkout one of them
    // 2. get specific copy and checkout if available
    // 3. renew loan if already exists

        // SELECT c FROM Copy c WHERE c.isbn = :isbn AND status = available LIMIT 1
        copyRepository.findFirstByIsbnAndStatus(isbn, Availability.AVAILABLE.name)?.let { copy ->
            loanRepository.save(
                Loan(
                    copy = copy,
                    member = member,
                    lengthDays = lengthDays,
                    loanDate = Date(),
                )
            )
        } ?: throw NoCopyAvailableForBookException(isbn)

}