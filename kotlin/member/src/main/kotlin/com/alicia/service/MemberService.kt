package com.alicia.service

import com.alicia.data.Member
import com.alicia.repository.MemberRepository
import java.util.UUID
import javax.inject.Singleton

@Singleton
class MemberService(
    private val memberRepository: MemberRepository
) {
    fun createMember(data: Member): String? = memberRepository.save(data)

    fun getMember(uuid: UUID) = memberRepository.findById(uuid)

    fun deleteMember(uuid: UUID) = memberRepository.deleteById(uuid)
}