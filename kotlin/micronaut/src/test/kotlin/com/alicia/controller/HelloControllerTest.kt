package com.alicia.controller

import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest
class HelloControllerTest {

    @Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    fun `WHEN get hello without name THEN respond with hello`() {
        val expectedResponse = "Hello"

        val actualResponse = client.toBlocking()
                .retrieve("/hello")

        assertEquals(expectedResponse,  actualResponse)
    }

    @Test
    fun `WHEN get hello with name THEN respond with hello, name`() {
        val expectedResponse = "Hello, tester!"

        val actualResponse = client.toBlocking()
                .retrieve("/hello?name=tester")

        assertEquals(expectedResponse,  actualResponse)
    }
}