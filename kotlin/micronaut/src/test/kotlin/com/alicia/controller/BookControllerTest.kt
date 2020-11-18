package com.alicia.controller

import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import javax.inject.Inject

@MicronautTest
class BookControllerTest {

    @Inject
    @field:Client("/book")
    lateinit var client: HttpClient
}