package com.alicia.controller

import com.alicia.data.Member
import com.alicia.service.MemberService
import com.mongodb.client.result.DeleteResult
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import java.util.UUID
import javax.inject.Inject

@Secured("true")
@Controller("/member")
class MemberController(
    private val memberService: MemberService
) {

    @Get("/{uuid}")
    fun getMember(@PathVariable uuid: UUID): Member? = memberService.getMember(uuid)

    @Post
    fun createMember(@Body member: Member): String? = memberService.createMember(member)

    @Put("/{uuid}")
    fun updateMember(@PathVariable uuid: UUID): String = TODO("Unimplemented")

    @Delete("/{uuid}")
    fun deleteMember(@PathVariable uuid: UUID): DeleteResult = memberService.deleteMember(uuid)

}