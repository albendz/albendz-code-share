package com.alicia.utils

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UtilsTest {

    @Test
    fun `WHEN genre string single word THEN return title case of single word`() {
        val inputs = listOf(
            "Fiction",
            "fiction",
            "FICTION",
            " Fiction    ",
            "  fiction \n \t  "
        )
        val expected = "Fiction"

        inputs.map { Utils.toGenreName(it) }.forEach {
            assertEquals("Expect $it to be $expected", expected, it)
        }
    }

    @Test
    fun `WHEN genre string multiple words THEN title case of each word`() {
        val inputs = listOf(
            "Science Fiction",
            "science fiction",
            "SCIENCE FICTION",
            "    science \t \n Fiction    ",
            " \n science   fiction \n \t  "
        )
        val expected = "Science Fiction"

        inputs.map { Utils.toGenreName(it) }.forEach {
            assertEquals("Expect $it to be $expected", expected, it)
        }
    }

    @Test
    fun `WHEN genre string has single character component THEN string in title case`() {
        assertEquals("Science Fiction B", Utils.toGenreName(" Science fiction b"))
    }

}