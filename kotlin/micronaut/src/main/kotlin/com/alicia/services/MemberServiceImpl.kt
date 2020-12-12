package com.alicia.services

import com.alicia.model.AddMemberRequest
import com.alicia.model.MemberResponse
import kotlin.jvm.Throws

class MemberServiceImpl: MemberService {

    @Throws()
    override fun addMember(member: AddMemberRequest): MemberResponse {
        TODO("Not yet implemented")
    }

    override fun getMember(email: String): MemberResponse {
        TODO("Not yet implemented")
    }
}