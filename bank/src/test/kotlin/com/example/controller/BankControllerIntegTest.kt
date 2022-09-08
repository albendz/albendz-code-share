package com.example.controller

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.kotest.annotation.MicronautTest

@MicronautTest
class BankControllerIntegTest(
    @Client("/bank") private val client: HttpClient
): BehaviorSpec({

    given("The bank controller") {
        `when`("index is called") {
            val result = client.toBlocking().retrieve("/")
            then("the result is a welcome message") {
                result shouldBe "Welcome to my simple banking application!"
            }
        }
    }
})