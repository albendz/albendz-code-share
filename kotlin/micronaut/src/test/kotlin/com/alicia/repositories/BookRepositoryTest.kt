package com.alicia.repositories

import com.alicia.constants.Availability
import com.alicia.data.Copy
import com.alicia.data.Loan
import com.alicia.data.Member
import com.alicia.fixtures.BookFixtures
import com.alicia.fixtures.MemberFixtures
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import java.util.Date
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

    @Test
    fun `WHEN checkout book with no copy ID then return first available copy`() {
        val copy: Copy? = Copy(UUID.randomUUID(), BookFixtures.defaultBook, Availability.UNAVAILABLE.name)
        val loan = Loan(copy = copy, member = MemberFixtures.member, lengthDays = 15, loanDate = Date())

        Mockito.`when`(
            copyRepository.findFirstByIsbnAndStatus(
                "DEFAULT_ISBN",
                ""
            )
        ).thenReturn(copy)
        Mockito.`when`(loanRepository.save(loan))
            .thenReturn(loan)
        Mockito.`when`(bookRepository.checkoutBook(
            MemberFixtures.member,
            BookFixtures.defaultBook.isbn!!,
            15
        )).thenCallRealMethod()

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
}