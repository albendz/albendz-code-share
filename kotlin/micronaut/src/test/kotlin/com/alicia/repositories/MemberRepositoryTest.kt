package com.alicia.repositories

import com.alicia.exceptions.NonUniqueMemberEmailException
import com.alicia.fixtures.MemberFixtures
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import java.sql.SQLException
import javax.inject.Inject
import junit.framework.Assert.assertEquals
import org.hibernate.exception.ConstraintViolationException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito

@MicronautTest
class MemberRepositoryTest {

    @Inject
    lateinit var memberRepository: MemberRepository

    @MockBean(MemberRepository::class)
    fun memberRepository(): MemberRepository = Mockito.mock(MemberRepository::class.java)

    @Test
    fun `WHEN save member with non-unique email THEN return non unique email exception`() {
        Mockito.`when`(memberRepository.save(MemberFixtures.member))
            .thenThrow(ConstraintViolationException("", SQLException(), ""))
        Mockito.`when`(memberRepository.saveMember(MemberFixtures.member)).thenCallRealMethod()

        assertThrows<NonUniqueMemberEmailException> {
            memberRepository.saveMember(MemberFixtures.member)
        }
    }

    @Test
    fun `WHEN save member THEN return saved member`() {
        Mockito.`when`(memberRepository.save(MemberFixtures.member))
            .thenReturn(MemberFixtures.member)
        Mockito.`when`(memberRepository.saveMember(MemberFixtures.member)).thenCallRealMethod()

        val member = memberRepository.saveMember(MemberFixtures.member)

        assertEquals(MemberFixtures.member, member)
    }
}