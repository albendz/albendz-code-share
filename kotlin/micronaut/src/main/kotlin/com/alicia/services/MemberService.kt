package com.alicia.services

import com.alicia.exceptions.GenericBadRequestException
import com.alicia.model.AddMemberRequest
import com.alicia.model.MemberResponse
import java.util.*

interface MemberService {

    @Throws(GenericBadRequestException::class)
    fun addMember(member: AddMemberRequest): MemberResponse

    fun getMember(uuid: UUID): MemberResponse
}