package com.alicia.repositories

import com.alicia.constants.Availability
import com.alicia.data.Copy
import com.alicia.data.Loan
import com.alicia.fixtures.BookFixtures
import com.alicia.fixtures.MemberFixtures
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import java.util.GregorianCalendar
import java.util.UUID
import javax.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

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
        it.clear()
        it.set(2021, 1, 3)
        it.time
    }

    @Test
    fun `WHEN checkout book with no copy ID then return first available copy`() {
        val copy: Copy? = Copy(UUID.randomUUID(), BookFixtures.defaultBook, Availability.AVAILABLE.name)
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
    }

    @Test
    fun `WHEN checkout book with no copy ID and no copies available then throw exception`() {
        TODO("Unimplemented")
    }

    @Test
    fun `WHEN checkout book with copy ID and copy is available then return loan for copy`() {
        TODO("Unimplemented")
    }

    @Test
    fun `WHEN checkout book with copy ID and copy unavailable and checked out to current member then renew`() {
        TODO("Unimplemented")
    }

    @Test
    fun `WHEN checkout book with copy ID, copy unavailable, and not checked out to current member then throw exception`() {
        TODO("Unimplemented")
    }
}