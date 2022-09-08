package com.example.controller

import com.example.model.CreateCustomerRequest
import com.example.model.CustomerResponse
import com.example.service.CustomerService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import java.util.UUID

@Controller("/bank")
class BankController() {

    @Get(uri = "/", produces = ["text/plain"])
    @ApiResponses(
        ApiResponse(
            description = "Display bank welcome message",
            responseCode = "200",
        )
    )
    fun index(): String = "Welcome to my simple banking application!"

}