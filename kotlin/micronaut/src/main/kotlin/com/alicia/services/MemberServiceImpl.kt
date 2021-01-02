package com.alicia.services

import com.alicia.data.Member
import com.alicia.exceptions.MemberAlreadyExistsWithEmailException
import com.alicia.exceptions.MemberNotFoundException
import com.alicia.exceptions.NonUniqueMemberEmailException
import com.alicia.model.AddMemberRequest
import com.alicia.model.MemberResponse
import com.alicia.repositories.MemberRepository
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.jvm.Throws

@Singleton
class MemberServiceImpl : MemberService {

    @Inject
    lateinit var memberRepository: MemberRepository

    @Throws(MemberAlreadyExistsWithEmailException::class)
    override fun addMember(addMemberRequest: AddMemberRequest): Member =
        addMemberRequest.toMember().let { member ->
            try {
                memberRepository.saveMember(member)
            } catch (e: NonUniqueMemberEmailException) {
                throw MemberAlreadyExistsWithEmailException()
            }
        }

    @Throws(MemberNotFoundException::class)
    override fun getMember(uuid: UUID): Member =
        memberRepository.findFirstById(uuid) ?: throw MemberNotFoundException()

}
