package com.alicia.fixtures

import com.alicia.data.Member
import com.alicia.model.AddMemberRequest
import com.alicia.model.MemberResponse
import java.util.UUID

object MemberFixtures {
    val defaultUuid = UUID.randomUUID()
    val addMemberRequest = AddMemberRequest(
        email = "person@people.com",
        firstName = "Jane",
        lastName = "Doe",
    )
    val memberResponse = MemberResponse(
        id = defaultUuid,
        email = addMemberRequest.email,
        firstName = addMemberRequest.firstName,
        lastName = addMemberRequest.lastName,
    )
    val member = Member(
        id = defaultUuid,
        email = addMemberRequest.email,
        firstName = addMemberRequest.firstName,
        lastName = addMemberRequest.lastName,
    )
}