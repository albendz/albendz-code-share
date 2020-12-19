package com.alicia.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AddMemberRequestTest {

    @Test
    fun `WHEN create member with blank first name THEN return errors`() {
        assertEquals(
            listOf("First Name"),
            AddMemberRequest("email@email.com", "", "last")
                .getErrors()
        )
    }

    @Test
    fun `WHEN create member with blank last name THEN return errors`() {
        assertEquals(
            listOf("Last Name"),
            AddMemberRequest("email@email.com", "first", "")
                .getErrors()
        )
    }

    @Test
    fun `WHEN request create member with invalid email THEN return errors`() {
        assertEquals(
            listOf("Email"),
            AddMemberRequest("invalid email", "first", "last")
                .getErrors()
        )
    }

    @Test
    fun `WHEN request create member with no valid fields THEN return errors`() {
        assertEquals(
            listOf("First Name", "Last Name", "Email"),
            AddMemberRequest("invalid email", "", "")
                .getErrors()
        )
    }
}