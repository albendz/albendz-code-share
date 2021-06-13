package com.alicia.repositories

import com.alicia.configuration.CalendarManager
import com.alicia.constants.Availability
import com.alicia.data.Book
import com.alicia.data.Copy
import com.alicia.data.Loan
import com.alicia.exceptions.BookMissingRequiredDataException
import com.alicia.exceptions.BookWithIsbnAlreadyExistsException
import com.alicia.exceptions.NoCopyAvailableForBookException
import com.alicia.fixtures.AuthorFixtures
import com.alicia.fixtures.BookFixtures
import com.alicia.fixtures.MemberFixtures
import com.nhaarman.mockitokotlin2.any
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import java.sql.SQLException
import java.time.LocalDate
import java.time.ZoneId
import java.util.GregorianCalendar
import java.util.UUID
import javax.inject.Inject
import org.hibernate.exception.ConstraintViolationException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoInteractions

@MicronautTest
class BookRepositoryTest {

    @Inject
    lateinit var bookRepository: BookRepository

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

    @MockBean(AuthorRepository::class)
    fun authorRepository(): AuthorRepository = mock(AuthorRepository::class.java)

    @MockBean(GenreRepository::class)
    fun genreRepository(): GenreRepository = mock(GenreRepository::class.java)

    @MockBean(CopyRepository::class)
    fun copyRepository(): CopyRepository = mock(CopyRepository::class.java)

    @MockBean(LoanRepository::class)
    fun loanRepository(): LoanRepository = mock(LoanRepository::class.java)

    @MockBean(BookRepository::class)
    fun bookRepository(): BookRepository = mock(BookRepository::class.java)

    private val dateToday = GregorianCalendar().let {
        val now = LocalDate.now(ZoneId.of("UTC"))
        it.clear()
        it.set(now.year, now.month.value - 1, now.dayOfMonth)
        it.time
    }

    @BeforeEach
    fun setup() {
        `when`(bookRepository.copyRepository).thenReturn(copyRepository)
        `when`(bookRepository.authorRepository).thenReturn(authorRepository)
        `when`(bookRepository.genreRepository).thenReturn(genreRepository)
        `when`(bookRepository.loanRepository).thenReturn(loanRepository)
        `when`(bookRepository.calendarManager).thenReturn(calendarManager)
    }

    @Test
    fun `WHEN checkout book with no copy ID then return first available copy`() {
        val copy = Copy(UUID.randomUUID(), BookFixtures.defaultBook, Availability.AVAILABLE.name)
        val loan = Loan(copy = copy, member = MemberFixtures.member, lengthDays = 15, loanDate = dateToday)

        `when`(
            copyRepository.findFirstByIsbnAndStatus(
                BookFixtures.defaultBook.isbn!!,
                Availability.AVAILABLE.name,
            )
        ).thenReturn(copy)
        `when`(loanRepository.save(loan))
            .thenReturn(loan)
        `when`(
            bookRepository.checkoutBook(
                MemberFixtures.member,
                BookFixtures.defaultBook.isbn!!,
                15
            )
        ).thenCallRealMethod()
        `when`(bookRepository.getAvailableCopyByIsbnOrCopyId(BookFixtures.defaultBook.isbn!!, null))
            .thenCallRealMethod()

        val actualLoan = bookRepository.checkoutBook(
            MemberFixtures.member,
            BookFixtures.defaultBook.isbn!!,
            15
        )

        assertEquals(loan, actualLoan)
        assertEquals(Availability.UNAVAILABLE.name, copy.status)
        verify(copyRepository, times(1)).update(copy)
    }

    @Test
    fun `WHEN checkout book with no copy ID and no copies available THEN throw exception`() {
        `when`(
            copyRepository.findFirstByIsbnAndStatus(
                BookFixtures.defaultBook.isbn!!,
                Availability.AVAILABLE.name,
            )
        ).thenReturn(null)
        `when`(
            bookRepository.checkoutBook(
                MemberFixtures.member,
                BookFixtures.defaultBook.isbn!!,
                15,
            )
        ).thenCallRealMethod()
        `when`(bookRepository.getAvailableCopyByIsbnOrCopyId(BookFixtures.defaultBook.isbn!!, null))
            .thenCallRealMethod()

        assertThrows<NoCopyAvailableForBookException> {
            bookRepository.checkoutBook(
                MemberFixtures.member,
                BookFixtures.defaultBook.isbn!!,
                15,
            )
        }

        verifyNoInteractions(loanRepository)
    }

    @Test
    fun `WHEN checkout book with copy ID and copy is available then return loan for copy`() {
        val copy = Copy(UUID.randomUUID(), BookFixtures.defaultBook, Availability.AVAILABLE.name)
        val loan = Loan(copy = copy, member = MemberFixtures.member, lengthDays = 15, loanDate = dateToday)

        `when`(
            copyRepository.findFirstByCopyIdAndIsbn(copy.copyId!!, BookFixtures.defaultBook.isbn!!)
        ).thenReturn(copy)
        `when`(loanRepository.save(loan))
            .thenReturn(loan)
        `when`(
            bookRepository.checkoutBook(
                MemberFixtures.member,
                BookFixtures.defaultBook.isbn!!,
                15,
                copy.copyId
            )
        ).thenCallRealMethod()
        `when`(bookRepository.getAvailableCopyByIsbnOrCopyId(BookFixtures.defaultBook.isbn!!, copy.copyId))
            .thenCallRealMethod()

        val actualLoan = bookRepository.checkoutBook(
            MemberFixtures.member,
            BookFixtures.defaultBook.isbn!!,
            15,
            copy.copyId
        )

        assertEquals(loan, actualLoan)
        assertEquals(Availability.UNAVAILABLE.name, copy.status)
        verify(copyRepository, times(1)).update(copy)
    }

    @Test
    fun `WHEN checkout book with copy ID and copy unavailable and checked out to current member then renew`() {
        val olderDate = GregorianCalendar().let {
            it.clear()
            it.set(2020, 11, 31)
            it.time
        }
        val copy = Copy(UUID.randomUUID(), BookFixtures.defaultBook, Availability.UNAVAILABLE.name)
        val oldLoan = Loan(copy = copy, member = MemberFixtures.member, lengthDays = 15, loanDate = olderDate)
        val newLoan = Loan(copy = copy, member = MemberFixtures.member, lengthDays = 15, loanDate = dateToday)

        `when`(
            copyRepository.findFirstByCopyIdAndIsbn(copy.copyId!!, BookFixtures.defaultBook.isbn!!)
        ).thenReturn(copy)
        `when`(loanRepository.findFirstByCopy(copy))
            .thenReturn(oldLoan)
        `when`(loanRepository.update(newLoan)).thenReturn(newLoan)
        `when`(
            bookRepository.checkoutBook(
                MemberFixtures.member,
                BookFixtures.defaultBook.isbn!!,
                15,
                copy.copyId
            )
        ).thenCallRealMethod()
        `when`(bookRepository.getAvailableCopyByIsbnOrCopyId(BookFixtures.defaultBook.isbn!!, copy.copyId))
            .thenCallRealMethod()

        val actualLoan = bookRepository.checkoutBook(
            MemberFixtures.member,
            BookFixtures.defaultBook.isbn!!,
            15,
            copy.copyId
        )

        assertEquals(newLoan, actualLoan)
    }

    @Test
    fun `WHEN checkout book with copy ID, copy unavailable, and not checked out to current member then throw exception`() {
        val copy = Copy(UUID.randomUUID(), BookFixtures.defaultBook, Availability.UNAVAILABLE.name)
        val loan = Loan(
            copy = copy,
            member = MemberFixtures.member.copy(UUID.randomUUID()),
            lengthDays = 15,
            loanDate = dateToday
        )

        `when`(bookRepository.copyRepository).thenReturn(copyRepository)
        `when`(
            copyRepository.findFirstByCopyIdAndIsbn(copy.copyId!!, BookFixtures.defaultBook.isbn!!)
        ).thenReturn(copy)
        `when`(loanRepository.findFirstByCopy(copy))
            .thenReturn(loan)
        `when`(
            bookRepository.checkoutBook(
                MemberFixtures.member,
                BookFixtures.defaultBook.isbn!!,
                15,
                copy.copyId
            )
        ).thenCallRealMethod()
        `when`(bookRepository.getAvailableCopyByIsbnOrCopyId(BookFixtures.defaultBook.isbn!!, copy.copyId))
            .thenCallRealMethod()

        assertThrows<NoCopyAvailableForBookException> {
            bookRepository.checkoutBook(
                MemberFixtures.member,
                BookFixtures.defaultBook.isbn!!,
                15,
                copy.copyId
            )
        }
    }

    @Test
    fun `WHEN save with author and genre with no ISBN then throw exception`() {
        `when`(bookRepository.saveWithAuthorAndGenreOrReturnExisting(Book())).thenCallRealMethod()
        assertThrows<BookMissingRequiredDataException> {
            `when`(
                bookRepository.saveWithAuthorAndGenreOrReturnExisting(
                    Book()
                )
            ).thenCallRealMethod()
        }
    }

    @Test
    fun `WHEN save with author and genre for existing book THEN return existing book`() {
        val book = BookFixtures.defaultBook.copy()
        val copy = Copy(book = book, status = Availability.AVAILABLE.name)
        book.copies = listOf(copy)

        `when`(bookRepository.findFirstByIsbn(book.isbn!!)).thenReturn(book)
        `when`(bookRepository.saveWithAuthorAndGenreOrReturnExisting(book)).thenCallRealMethod()
        `when`(copyRepository.save(copy)).thenReturn(copy.copy(UUID.randomUUID()));

        val result = bookRepository.saveWithAuthorAndGenreOrReturnExisting(book)

        assertEquals(book, result)
        verify(copyRepository).save(copy)
    }

    @Test
    fun `WHEN save with author and genre with existing author THEN use existing author`() {
        val book = BookFixtures.defaultBook

        `when`(bookRepository.findFirstByIsbn(book.isbn!!)).thenReturn(null)
        `when`(bookRepository.saveWithAuthorAndGenreOrReturnExisting(book)).thenCallRealMethod()
        `when`(authorRepository.findClosestMatchAuthor(book.author!!))
            .thenReturn(AuthorFixtures.defaultAuthor)
        `when`(bookRepository.saveBookAndGenre(book, AuthorFixtures.defaultAuthor)).thenReturn(book)

        val result = bookRepository.saveWithAuthorAndGenreOrReturnExisting(book)

        assertEquals(book, result)
        verify(authorRepository, times(0)).save(book.author!!)
        fail<Unit>("Test save copies as well")
    }

    @Test
    fun `WHEN save with author and genre for new author THEN save new author`() {
        val book = BookFixtures.defaultBook

        `when`(authorRepository.findClosestMatchAuthor(book.author!!))
            .thenReturn(null)
        `when`(authorRepository.save(book.author!!)).thenReturn(AuthorFixtures.defaultAuthor)
        `when`(bookRepository.saveBookAndGenre(book, AuthorFixtures.defaultAuthor)).thenReturn(book)
        `when`(bookRepository.findFirstByIsbn(book.isbn!!)).thenReturn(null)
        `when`(bookRepository.saveWithAuthorAndGenreOrReturnExisting(book)).thenCallRealMethod()

        val result = bookRepository.saveWithAuthorAndGenreOrReturnExisting(book)

        assertEquals(book, result)
    }

    @Test
    fun `WHEN save with author and genre for null author THEN no author repository interaction`() {
        val book = BookFixtures.defaultBook.copy(author = null)

        `when`(bookRepository.findFirstByIsbn(book.isbn!!)).thenReturn(null)
        `when`(bookRepository.saveWithAuthorAndGenreOrReturnExisting(book)).thenCallRealMethod()

        assertThrows<BookMissingRequiredDataException> {
            bookRepository.saveWithAuthorAndGenreOrReturnExisting(book)
        }
    }

    @Test
    fun `WHEN save with author and genre with existing genre THEN use existing genre`() {
        val book = BookFixtures.defaultBook
        val existingGenre = book.genre?.copy(id = UUID.randomUUID())
        val author = AuthorFixtures.defaultAuthor
        val expectedBook = book.copy(author = author, genre = existingGenre)

        `when`(genreRepository.findFirstByName(book.genre?.name)).thenReturn(existingGenre)
        `when`(bookRepository.save(any())).thenReturn(book)
        `when`(bookRepository.saveBookAndGenre(book, author)).thenCallRealMethod()

        val savedBook = bookRepository.saveBookAndGenre(book, author)

        assertEquals(expectedBook, savedBook)
    }

    @Test
    fun `WHEN save with author and genre for new genre THEN save new genre`() {
        val book = BookFixtures.defaultBook
        val author = AuthorFixtures.defaultAuthor
        val expectedBook = book.copy(author = author, genre = book.genre)

        `when`(genreRepository.findFirstByName(book.genre?.name)).thenReturn(null)
        `when`(genreRepository.save(any())).thenReturn(book.genre)
        `when`(bookRepository.save(any())).thenReturn(book)
        `when`(bookRepository.saveBookAndGenre(book, author)).thenCallRealMethod()

        val savedBook = bookRepository.saveBookAndGenre(book, author)

        assertEquals(expectedBook, savedBook)
        verify(genreRepository, times(1)).save(any())
    }

    @Test
    fun `WHEN save with author and genre for null genre THEN no genre repository interaction`() {
        val book = BookFixtures.defaultBook.copy(genre = null)
        val author = AuthorFixtures.defaultAuthor
        val expectedBook = book.copy(author = author,genre = null)

        `when`(bookRepository.save(any())).thenReturn(book)
        `when`(bookRepository.saveBookAndGenre(book, author)).thenCallRealMethod()

        val savedBook = bookRepository.saveBookAndGenre(book, author)

        assertEquals(expectedBook, savedBook)
        verifyNoInteractions(genreRepository)
    }

    @Test
    fun `WHEN constraint violation exception on save book with author and genre then throw internal exception`() {
        val book = BookFixtures.defaultBook
        val existingGenre = book.genre?.copy(id = UUID.randomUUID())
        val author = AuthorFixtures.defaultAuthor

        `when`(genreRepository.findFirstByName(book.genre?.name)).thenReturn(existingGenre)
        `when`(bookRepository.save(any()))
            .thenThrow(ConstraintViolationException("", SQLException(), ""))
        `when`(bookRepository.saveBookAndGenre(book, author)).thenCallRealMethod()

        assertThrows<BookWithIsbnAlreadyExistsException> { bookRepository.saveBookAndGenre(book, author) }
    }

    @Test
    fun `WHEN find book with copies THEN return book and copies`() {
        fail<Unit>();
    }

    @Test
    fun `WHEN find book with copies for invalid book THEN return null`() {
        fail<Unit>();
    }

}