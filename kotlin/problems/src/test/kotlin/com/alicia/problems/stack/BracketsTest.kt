package com.alicia.problems.stack

import com.alicia.problems.com.alicia.problems.stack.Brackets
import com.alicia.problems.com.alicia.problems.stack.InvalidCharacterException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class BracketsTest {
    private val brackets = Brackets()

    @Test
    fun `Leetcode test one - WHEN one set of matched brackets THEN true`() {
        assertTrue(brackets.matchedBrackets("()"))
    }

    @Test
    fun `Leetcode test two - WHEN three sets of complete brackets THEN true`() {
        assertTrue(brackets.matchedBrackets("()[]{}"))
    }

    @Test
    fun `Leetcode test three - WHEN different open and close brackets THEN false`() {
        assertFalse(brackets.matchedBrackets("(]"))
    }

    @Test
    fun `Leetcode test four - WHEN out of order open and close THEN false`() {
        assertFalse(brackets.matchedBrackets("([)]"))
    }

    @Test
    fun `Leetcode test five - WHEN  nested matched brackets THEN true`() {
        assertTrue(brackets.matchedBrackets("{[]}"))
    }

    @Test
    fun `WHEN only close brackets THEN false`() {
        assertFalse(brackets.matchedBrackets("))"))
    }

    @Test
    fun `WHEN only open brackets THEN false`() {
        assertFalse(brackets.matchedBrackets("(("))
    }

    @Test
    fun `WHEN empty string THEN true`() {
        assertTrue(brackets.matchedBrackets(""))
    }

    @Test
    fun  `WHEN invalid characters AND valid brackets THEN throw exception`() {
        Assertions.assertThrows(InvalidCharacterException::class.java) {
            brackets.matchedBrackets("!{abc[--]nn}dfg")
        }
    }

    @Test
    fun `WHEN extra spaces or whitespace AND valid brackets THEN true`() {
        assertTrue(brackets.matchedBrackets("   {\t[\n]  }   "))
    }
}