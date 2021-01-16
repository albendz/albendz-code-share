package com.alicia.repositories

import com.alicia.configuration.CalendarManager
import com.alicia.constants.Availability
import com.alicia.data.*
import com.alicia.exceptions.BookWithIsbnAlreadyExists
import com.alicia.exceptions.NoCopyAvailableForBookException
import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.data.repository.CrudRepository
import io.micronaut.data.repository.PageableRepository
import java.util.UUID
import javax.inject.Inject
import javax.transaction.Transactional
import org.hibernate.exception.ConstraintViolationException

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

    @Inject
    lateinit var calendarManager: CalendarManager

    abstract fun findFirstByIsbn(isbn: String): Book?

    abstract fun existsByIsbn(isbn: String?): Boolean

    @Transactional
    fun saveWithAuthorAndGenreOrReturnExisting(book: Book): Book {
        findFirstByIsbn(book.isbn!!)?.let {
            return book
        }

        val author = book.author?.let { author ->
            authorRepository.findFirstByFirstNameAndLastName(author.firstName, author.lastName)
                ?: authorRepository.save(author)
        }

        return saveBookAndGenre(book, author)
    }

    @Throws(BookWithIsbnAlreadyExists::class)
    @Transactional
    fun saveBookAndGenre(book: Book, author: Author?): Book {
        try {
            val genre = book.genre?.let { genre ->
                genreRepository.findFirstByName(book.genre?.name) ?: genreRepository.save(genre)
            }

            return book.apply {
                this.author = author
                this.genre = genre
            }.let {
                save(it)
            }.let {
                it.copies = it.copies.map { copy ->
                    copy.book = it
                    copyRepository.save(copy)
                }
                it
            }
        } catch (e: ConstraintViolationException) {
            throw BookWithIsbnAlreadyExists(book.isbn!!) // TODO
        }
    }

    @Query(
        value = "SELECT b FROM Book b WHERE (SELECT count(*) FROM Copy c " +
                "WHERE c.book = b AND status in :status) > :count",
        countQuery = "SELECT count(b) FROM Book b " +
                "WHERE (SELECT count(*) FROM Copy c WHERE c.book = b AND status in :status) > :count"
    )
    abstract fun findBooks(pageable: Pageable, status: List<String> = listOf("AVAILABLE"), count: Long = 0): Page<Book>

    @Throws(NoCopyAvailableForBookException::class)
    @Transactional
    fun checkoutBook(member: Member, isbn: String, lengthDays: Int, copyId: UUID? = null): Loan =
        getAvailableCopyByIsbnOrCopyId(isbn, copyId)?.let { copy ->
            when (copy.status) {
                Availability.AVAILABLE.name ->
                    loanRepository.save(
                        Loan(
                            copy = copy,
                            member = member,
                            lengthDays = lengthDays,
                            loanDate = calendarManager.getToday(),
                        )
                    ).also {
                        copyRepository.update(copy.apply { status = Availability.UNAVAILABLE.name })
                    }
                Availability.UNAVAILABLE.name ->
                    loanRepository.findFirstByCopy(copy)?.takeIf { loan -> loan.member == member }?.let { loan ->
                        loanRepository.update(
                            loan.apply { loanDate = calendarManager.getToday() }
                        )
                    }
                else -> null
            }
        } ?: throw NoCopyAvailableForBookException(isbn)

    private fun getAvailableCopyByIsbnOrCopyId(isbn: String, copyId: UUID?): Copy? =
        if (copyId != null)
            copyRepository.findFirstByCopyId(copyId)
        else {
            copyRepository.findFirstByIsbnAndStatus(isbn, Availability.AVAILABLE.name)
        }

}
