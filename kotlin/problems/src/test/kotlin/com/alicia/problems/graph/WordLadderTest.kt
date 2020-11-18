package com.alicia.problems.graph

import com.alicia.problems.com.alicia.problems.graph.WordLadder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class WordLadderTest {

    @Test
    fun `WHEN find word ladder THEN return word ladder path`() {
        val expected = listOf("HEAD", "TEAL", "TELL", "TALL", "TAIL")
        val result = WordLadder.findWordLadder("HEAD", "TAIL")

        assertEquals(expected.size, result?.size)
        for(i in 0..expected.size) {
            assertEquals(expected[i], result!![i], "Expected ${expected[i]} but was ${result[i]}")
        }
    }
}