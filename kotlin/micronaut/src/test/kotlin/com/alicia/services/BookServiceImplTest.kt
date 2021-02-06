package com.alicia.services

import com.alicia.data.Copy
import com.alicia.data.Loan
import com.alicia.data.Member
import com.alicia.exceptions.MemberNotFoundException
import com.alicia.exceptions.NoCopyAvailableException
import com.alicia.exceptions.NoCopyAvailableForBookException
import com.alicia.fixtures.BookFixtures
import com.alicia.fixtures.MemberFixtures
import com.alicia.model.CheckoutRequest
import com.alicia.model.PaginatedBookResponse
import com.alicia.repositories.BookRepository
import com.alicia.repositories.GenreRepository
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import java.util.Date
import java.util.UUID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import javax.inject.Inject
import org.junit.jupiter.api.assertThrows

@MicronautTest
class BookServiceImplTest {

    @Inject
    lateinit var bookService: BookServiceImpl

    @Inject
    lateinit var bookRepository: BookRepository

    @Inject
    lateinit var genreRepository: GenreRepository

    @Inject
    lateinit var memberService: MemberService

    @MockBean(BookRepository::class)
    fun bookRepository(): BookRepository = Mockito.mock(BookRepository::class.java)

    @MockBean(MemberService::class)
    fun memberService(): MemberService = Mockito.mock(MemberService::class.java)

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `WHEN search for books THEN pass page information to repository `() {
        val expectedPageable =  Pageable.from(5, 10)
        val page = Page.of(
                listOf(BookFixtures.defaultBook),
                expectedPageable,
                100
        )
        val expectedResponse = PaginatedBookResponse(
                numberOfPages = page.totalPages,
                currentPage = page.pageNumber,
                itemsOnPage = page.numberOfElements,
                totalItems = page.totalSize,
                books = page.content.map { it.toBookResponse() },
        )
        Mockito.`when`(bookRepository.findBooks(expectedPageable, emptyList())).thenReturn(page)

        val response = bookService.search(emptyList(), 5, 10)

        Mockito.verify(bookRepository).findBooks(expectedPageable, emptyList(), 0L)
        assertEquals(expectedResponse, response)
    }

    @Test
    fun `WHEN checkout book with valid member and valid loan THEN return loan`() {
        val checkoutRequest = CheckoutRequest(
            MemberFixtures.member.id!!
        )
        val member = MemberFixtures.member
        val copy = Copy(UUID.randomUUID(), BookFixtures.defaultBook, "AVAILABLE")
        val loan = Loan(UUID.randomUUID(), copy, Date(), 1, member)
        val expectedResponse = loan.toLoanResponse()

        Mockito.`when`(memberService.getMember(member.id!!)).thenReturn(member)
        Mockito.`when`(bookRepository.checkoutBook(member, BookFixtures.defaultIsbn, 1, null))
            .thenReturn(loan)

        val actualResponse = bookService.checkoutBook(BookFixtures.defaultIsbn,  checkoutRequest)

        assertEquals(expectedResponse, actualResponse)
    }

    @Test
    fun `WHEN checkout book with non-existing member THEN throw exception`() {
        val checkoutRequest = CheckoutRequest(
            MemberFixtures.member.id!!
        )

        Mockito.`when`(memberService.getMember(checkoutRequest.memberId)).thenThrow(MemberNotFoundException::class.java)

        assertThrows<MemberNotFoundException> {
            bookService.checkoutBook(BookFixtures.defaultIsbn, checkoutRequest)
        }

    }

    @Test
    fun `WHEN checkout book with valid member and no loan available THEN throw no copy exception`() {
        val checkoutRequest = CheckoutRequest(
            MemberFixtures.member.id!!
        )
        val member = MemberFixtures.member

        Mockito.`when`(memberService.getMember(member.id!!)).thenReturn(member)
        Mockito.`when`(bookRepository.checkoutBook(member, BookFixtures.defaultIsbn, 1, null))
            .thenThrow(NoCopyAvailableForBookException::class.java)

        assertThrows<NoCopyAvailableException> {
            bookService.checkoutBook(BookFixtures.defaultIsbn, checkoutRequest)
        }
    }

    @Test
    fun `WHEN adding book with genre THEN convert to valid genre on book`() {
        TODO("Unimplemented")
    }

    @Test
    fun `WHEN adding book without genre THEN no genre on book`() {
        TODO("Unimplemented")
    }

    @Test
    fun `WHEN adding book with existing author THEN add author to book`() {
        TODO("Unimplemented")
    }

    @Test
    fun `WHEN adding book with no author THEN add no author to book`() {
        TODO("Unimplemented")
    }
}
