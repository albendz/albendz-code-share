package com.alicia.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AddMemberRequestTest {

    @Test
    fun  `WHEN request create member with invalid email THEN return errors`() {
        assertEquals(
                "Email",
                AddMemberRequest("invalid email", "first", "last")
                        .getErrors().first()
        )
    }
}