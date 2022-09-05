package com.example.controller

import com.example.model.AccountResponse
import com.example.model.CreateCustomerRequest
import com.example.model.CustomerResponse
import com.example.service.CustomerService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import java.util.UUID

@Controller("/bank/customer/{customerId}/account")
class AccountController(
    private val customerService: CustomerService
) {

    @Get(uri = "/", produces = ["text/plain"])
    @ApiResponses(
        ApiResponse(
            description = "Display accounts for a customer",
            responseCode = "200",
        )
    )
    fun getAccounts(@PathVariable customerId: UUID): List<AccountResponse> =
        TODO("Unimplemented")

    @Get("/{accountId}")
    fun getAccount(@PathVariable customerId: UUID, @PathVariable accountId: UUID): AccountResponse =
        TODO("Unimplemented")

    @Post("/")
    fun createAccount(@PathVariable customerId: UUID): AccountResponse =
        TODO("Unimplemented")


    @Post("/{accountId}")
    fun updateAccountPost(@PathVariable customerId: UUID, @PathVariable accountId: UUID): AccountResponse =
        TODO("Unimplemented")

    @Put("/{accountId}")
    fun updateAccountPut(@PathVariable customerId: UUID, @PathVariable accountId: UUID): AccountResponse =
        TODO("Unimplemented")

    @Delete("/{accountId}")
    fun deleteAccount(@PathVariable customerId: UUID, @PathVariable accountId: UUID): Unit =
        TODO("Unimplemented")
}