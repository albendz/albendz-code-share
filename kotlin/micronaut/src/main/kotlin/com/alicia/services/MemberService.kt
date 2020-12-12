package com.alicia.services

import com.alicia.exceptions.GenericBadRequestException
import com.alicia.exceptions.MemberNotFoundException
import com.alicia.model.AddMemberRequest
import com.alicia.model.MemberResponse

interface MemberService {

    @Throws(GenericBadRequestException::class)
    fun addMember(member: AddMemberRequest): MemberResponse

    fun getMember(email: String): MemberResponse
}