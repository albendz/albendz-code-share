package com.alicia.controller

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue

@Controller
class HelloController {

    @Get("/hello")
    fun sayHello(@QueryValue name: String?): String =
            name?.let { n -> "Hello, $n!" } ?: "Hello"
}