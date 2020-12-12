package com.alicia.controller

import com.alicia.model.AddBookRequest
import com.alicia.model.AddMemberRequest
import com.alicia.model.BookResponse
import com.alicia.model.MemberResponse
import com.alicia.services.MemberService
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
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
    // Get user
    // Create user

    // Later:
    // Edit user info
    // Search loans by user (active, inactive)
    // Search holds by user (active, inactive)
}