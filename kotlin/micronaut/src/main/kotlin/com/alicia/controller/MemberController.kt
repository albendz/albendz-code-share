package com.alicia.controller

import com.alicia.model.AddMemberRequest
import com.alicia.model.MemberResponse
import com.alicia.services.MemberService
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import java.util.UUID
import javax.inject.Inject

@Controller("/member")
class MemberController {

    @Inject
    lateinit var memberService: MemberService

    @Post(consumes = ["application/json"], produces = ["application/json"])
    @ApiResponses(
        ApiResponse(
            description = "Create a member",
            responseCode = "201"
        ),
        ApiResponse(
            description = "Invalid member data provided or already exists",
            responseCode = "400"
        )
    )
    fun addMember(member: AddMemberRequest): MemberResponse {
        member.validate()
        return memberService.addMember(member)
    }

    @Get("/{id}")
    @ApiResponses(
        ApiResponse(
            description = "Return member with UUID",
            responseCode = "200"
        ),
        ApiResponse(
            description = "Member not found with UUID",
            responseCode = "404"
        )
    )
    fun getMember(@PathVariable id: UUID): MemberResponse = memberService.getMember(id)

    // Later:
    // Edit user info
    // Search loans by user (active, inactive)
    // Search holds by user (active, inactive)
}