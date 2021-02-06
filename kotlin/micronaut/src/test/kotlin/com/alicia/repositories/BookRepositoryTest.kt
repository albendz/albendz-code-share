package com.alicia.repositories

import com.alicia.constants.Availability
import com.alicia.data.Copy
import com.alicia.data.Loan
import com.alicia.exceptions.NoCopyAvailableForBookException
import com.alicia.fixtures.BookFixtures
import com.alicia.fixtures.MemberFixtures
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import java.time.LocalDate
import java.time.ZoneId
import java.util.GregorianCalendar
import java.util.UUID
import javax.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
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

    @MockBean(AuthorRepository::class)
    fun authorRepository(): AuthorRepository = mock(AuthorRepository::class.java)

    @MockBean(GenreRepository::class)
    fun genreRepository(): GenreRepository = mock(GenreRepository::class.java)

    @MockBean(CopyRepository::class)
    fun copyRepository(): CopyRepository = mock(CopyRepository::class.java)

    @MockBean(LoanRepository::class)
    fun loanRepository(): LoanRepository = mock(LoanRepository::class.java)

    private val dateToday = GregorianCalendar().let {
        val now = LocalDate.now(ZoneId.of("UTC"))
        it.clear()
        it.set(now.year, now.month.value - 1, now.dayOfMonth)
        it.time
    }

    @Test
    fun `WHEN checkout book with no copy ID then return first available copy`() {
        val copy = Copy(UUID.randomUUID(), BookFixtures.defaultBook, Availability.AVAILABLE.name)
        val loan = Loan(copy = copy, member = MemberFixtures.member, lengthDays = 15, loanDate = dateToday)

        Mockito.`when`(
            copyRepository.findFirstByIsbnAndStatus(
                BookFixtures.defaultBook.isbn!!,
                Availability.AVAILABLE.name,
            )
        ).thenReturn(copy)
        Mockito.`when`(loanRepository.save(loan))
            .thenReturn(loan)

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
    fun `WHEN checkout book with no copy ID and no copies available then throw exception`() {
        Mockito.`when`(
            copyRepository.findFirstByIsbnAndStatus(
                BookFixtures.defaultBook.isbn!!,
                Availability.AVAILABLE.name,
            )
        ).thenReturn(null)

        assertThrows<NoCopyAvailableForBookException> {
            bookRepository.checkoutBook(
                MemberFixtures.member,
                BookFixtures.defaultBook.isbn!!,
                15
            )
        }

        verifyNoInteractions(loanRepository)
    }

    @Test
    fun `WHEN checkout book with copy ID and copy is available then return loan for copy`() {
        val copy = Copy(UUID.randomUUID(), BookFixtures.defaultBook, Availability.AVAILABLE.name)
        val loan = Loan(copy = copy, member = MemberFixtures.member, lengthDays = 15, loanDate = dateToday)

        Mockito.`when`(
            copyRepository.findFirstByCopyId(copy.copyId!!)
        ).thenReturn(copy)
        Mockito.`when`(loanRepository.save(loan))
            .thenReturn(loan)

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

        Mockito.`when`(
            copyRepository.findFirstByCopyId(copy.copyId!!)
        ).thenReturn(copy)
        Mockito.`when`(loanRepository.findFirstByCopy(copy))
            .thenReturn(oldLoan)
        Mockito.`when`(loanRepository.update(newLoan)).thenReturn(newLoan)

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
        val loan = Loan(copy = copy, member = MemberFixtures.member.copy(UUID.randomUUID()), lengthDays = 15, loanDate = dateToday)

        Mockito.`when`(
            copyRepository.findFirstByCopyId(copy.copyId!!)
        ).thenReturn(copy)
        Mockito.`when`(loanRepository.findFirstByCopy(copy))
            .thenReturn(loan)

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
        TODO("Unimplemented")
    }

    @Test
    fun `WHEN save with author and genre for existing THEN return existing book`() {
        TODO("Unimplemented")
    }

    @Test
    fun `WHEN save with author and genre with existing author then use existing author`() {
        TODO("Unimplemented")
    }

    @Test
    fun `WHEN save with author and genre for new author THEN save new author`() {
        TODO("Unimplemented")
    }

    @Test
    fun `WHEN save with author and genre for null author THEN no author repository interaction`() {
        TODO("Unimplemented")
    }

    @Test
    fun `WHEN save with author and genre with existing genre then use existing genre`() {
        TODO("Unimplemented")
    }

    @Test
    fun `WHEN save with author and genre for new genre THEN save new genre`() {
        TODO("Unimplemented")
    }

    @Test
    fun `WHEN save with author and genre for null genre THEN no genre repository interaction`() {
        TODO("Unimplemented")
    }

    @Test
    fun `WHEN contraint violation exception on save book with author and genre then throw internal exception`() {
        TODO("Unimplemented")
    }
}