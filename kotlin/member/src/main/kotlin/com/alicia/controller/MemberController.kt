package com.alicia.controller

import com.alicia.service.MemberService
import io.micronaut.http.annotation.*

@Controller("/member-management")
class MemberController(
    val memberService: MemberService
) {

    @Get("/{uuid}")
    fun getMember(): String = TODO("Unimplemented")

    @Post("/create")
    fun createMember(): String = TODO("Unimplemented")

    @Put("/{uuid}/update")
    fun updateMember(): String = TODO("Unimplemented")

    @Delete("/{uuid}")
    fun deleteMember(): String = TODO("Unimplemented")

}