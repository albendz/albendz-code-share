package com.alicia.controller

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.Test

@MicronautTest
class GenreControllerTest {

    @Test
    fun `WHEN get genres with no parameters THEN use default parameters`() {
        TODO("Unimplemented")
    }

    @Test
    fun `WHEN get genres with non-default parameters THEN override defaults`() {
        TODO("Unimplemented")
    }

    @Test
    fun `WHEN get genre by ID with existing genre THEN return genre`() {
        TODO("Unimplemented")
    }

    @Test
    fun `WHEN get genre by ID not found THEN return not found exception`() {
        TODO("Unimplemented")
    }

    @Test
    fun `WHEN get genre by name with existing genre THEN return genre`() {
        TODO("Unimplemented")
    }

    @Test
    fun `WHEN get genre by name not found THEN return not found exception`() {
        TODO("Unimplemented")
    }

    @Test
    fun `WHEN get genre by name with invalid name length THEN return invalid request`() {
        TODO("Unimplemented")
    }
}