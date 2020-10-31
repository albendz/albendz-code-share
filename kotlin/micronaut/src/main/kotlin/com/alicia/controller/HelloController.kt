package com.alicia.controller

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue

@Controller
class HelloController {

    @Get("/hello")
    fun sayHello(@QueryValue name: String?): String = if (name == null) "Hello" else "Hello, $name!"
}