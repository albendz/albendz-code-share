package com.alicia.services

import com.alicia.exceptions.MemberAlreadyExistsWithEmailException
import com.alicia.exceptions.MemberNotFoundException
import com.alicia.exceptions.NonUniqueMemberEmailException
import com.alicia.fixtures.MemberFixtures
import com.alicia.repositories.MemberRepository
import io.micronaut.http.HttpStatus
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import javax.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@MicronautTest
class MemberServiceImplTest {

    @Inject
    lateinit var memberService: MemberServiceImpl

    @Inject
    lateinit var memberRepository: MemberRepository

    @MockBean(MemberRepository::class)
    fun memberRepository(): MemberRepository = mock(MemberRepository::class.java)

    @Test
    fun `WHEN add member THEN save member with details`() {
        val memberRequest = MemberFixtures.addMemberRequest
        `when`(memberRepository.saveMember(memberRequest.toMember())).thenReturn(MemberFixtures.member)

        val actualMember = memberService.addMember(memberRequest)

        assertEquals(MemberFixtures.member, actualMember)
    }

    @Test
    fun `WHEN add member already exists with email THEN throw already exists`() {
        val memberRequest = MemberFixtures.addMemberRequest
        `when`(memberRepository.saveMember(memberRequest.toMember())).thenThrow(NonUniqueMemberEmailException())

        val exception = assertThrows<MemberAlreadyExistsWithEmailException> {
            memberService.addMember(memberRequest)
        }

        assertEquals(HttpStatus.BAD_REQUEST, exception.httpStatus)
    }

    @Test
    fun `WHEN get member exists THEN return existing member with ID`() {
        `when`(memberRepository.findFirstById(MemberFixtures.defaultUuid)).thenReturn(MemberFixtures.member)

        val actualMember = memberService.getMember(MemberFixtures.defaultUuid)

        assertEquals(MemberFixtures.member, actualMember)
    }

    @Test
    fun `WHEN get member does not exist THEN throw not found`() {
        `when`(memberRepository.findFirstById(MemberFixtures.defaultUuid)).thenReturn(null)

        val exception = assertThrows<MemberNotFoundException> {
            memberService.getMember(MemberFixtures.defaultUuid)
        }

        assertEquals(HttpStatus.NOT_FOUND, exception.httpStatus)
    }

}