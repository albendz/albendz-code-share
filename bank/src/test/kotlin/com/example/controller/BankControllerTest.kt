package com.example.controller

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.spec.style.Test
import io.kotest.matchers.shouldBe

@Test
class BankControllerTest: BehaviorSpec({

    given("The bank controller") {
        `when`("index is called") {
            val result = BankController().index()
            then("the result is a welcome message") {
                result shouldBe "Welcome to my simple banking application!"
            }
        }
    }
})