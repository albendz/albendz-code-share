package com.alicia.controller

import com.alicia.service.AuthenticationService
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.security.authentication.AuthenticationProvider
import io.micronaut.security.authentication.AuthenticationResponse

@Controller("/auth")
class AuthenticationController(
    val authenticationService: AuthenticationService
) {//: AuthenticationProvider {

    @Post
    fun authenticate():  AuthenticationResponse = TODO("Unimplemented")
}