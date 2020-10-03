package com.alicia.problems.stack

import com.alicia.problems.com.alicia.problems.stack.Brackets
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class BracketsTest {
    val brackets = Brackets()

    @Test
    fun `Matched bracket string returns true`() {
        assertTrue(brackets.matchedBrackets("()"))
    }

    @Test
    fun `Unmatched bracket string returns false for only closed`() {
        assertFalse(brackets.matchedBrackets("))"))
    }

    @Test
    fun `Unmatched bracket string returns false for only open`() {
        assertFalse(brackets.matchedBrackets("(("))
    }

    @Test
    fun `Empty brackets string returns true`() {
        assertTrue(brackets.matchedBrackets(""))
    }
}