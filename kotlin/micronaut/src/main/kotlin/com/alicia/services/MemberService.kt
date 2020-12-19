package com.alicia.services

import com.alicia.exceptions.GenericBadRequestException
import com.alicia.exceptions.MemberAlreadyExistsWithEmailException
import com.alicia.exceptions.MemberNotFoundException
import com.alicia.model.AddMemberRequest
import com.alicia.model.MemberResponse
import java.util.UUID
import kotlin.jvm.Throws

interface MemberService {

    @Throws(MemberAlreadyExistsWithEmailException::class)
    fun addMember(addMemberRequest: AddMemberRequest): MemberResponse

    @Throws(MemberNotFoundException::class)
    fun getMember(uuid: UUID): MemberResponse

}