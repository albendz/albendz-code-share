package com.alicia.problems.graph

import com.alicia.problems.com.alicia.problems.graph.WordLadder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class WordLadderTest {

    @Test
    fun `WHEN find word ladder THEN return word ladder path`() {
        val expected = listOf("HEAD", "HEAL", "TEAL", "TELL", "TALL", "TAIL")
        val result = WordLadder.findWordLadder("HEAD", "TAIL")

        assertEquals(expected.size, result?.size)
        for(i in expected.indices) {
            assertEquals(expected[i], result!![i], "Expected ${expected[i]} but was ${result[i]}")
        }
    }

    @Test
    fun `WHEN find word ladder with same inputs THEN return list of first input`() {
        val result = WordLadder.findWordLadder("HEAD", "HEAD")

        assertEquals(listOf("HEAD"), result)
    }

    @Test
    fun `WHEN find word ladder with not solution THEN return empty list`() {
        val result = WordLadder.findWordLadder("HEAD", "VERY")

        assertTrue(result.isEmpty())
    }

    @Test
    fun `WHEN find word ladder with invalid input THEN return empty list`() {
        assertTrue(WordLadder.findWordLadder("AAAA", "TAIL").isEmpty(), "Invalid start returns empty list")
        assertTrue(WordLadder.findWordLadder("HEAD", "AAAA").isEmpty(),  "Invalid end returns empty list")
        assertTrue(WordLadder.findWordLadder("AAAA", "BBBB").isEmpty(),  "Invalid start and returns empty list")
    }

    @Test
    fun `WHEN find word ladder with different input lengths THEN return empty list`() {
        assertTrue(WordLadder.findWordLadder("HEADS", "TAIL").isEmpty(), "Invalid start returns empty list")
    }
}