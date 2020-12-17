package com.alicia.fixtures

import com.alicia.model.AddMemberRequest

object MemberFixtures {
    val addMemberRequest = AddMemberRequest(
        email = "person@people.com",
        firstName = "Jane",
        lastName = "Doe",
    )
}