package com.alicia.repositories

import com.alicia.data.Book
import com.alicia.data.Copy
import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.data.repository.CrudRepository
import io.micronaut.data.repository.PageableRepository
import javax.inject.Inject
import javax.transaction.Transactional

@Repository
abstract class BookRepository: CrudRepository<Book, String>, PageableRepository<Book, String> {

    @Inject
    lateinit var authorRepository: AuthorRepository

    @Inject
    lateinit var genreRepository: GenreRepository

    @Inject
    lateinit var copyRepository: CopyRepository

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

    @Query(value = "SELECT b FROM Book b WHERE (SELECT count(*) FROM Copy c " +
            "WHERE c.book = b AND status in :status) > :count",
            countQuery = "SELECT count(b) FROM Book b " +
                    "WHERE (SELECT count(*) FROM Copy c WHERE c.book = b AND status in :status) > :count"
    )
    abstract fun findBooks(pageable: Pageable, status: List<String> = listOf("AVAILABLE"), count: Long = 0): Page<Book>

}