package com.example

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
class BankController(
    private val customerService: CustomerService
) {

    @Get(uri = "/", produces = ["text/plain"])
    @ApiResponses(
        ApiResponse(
            description = "Display bank welcome message",
            responseCode = "200",
        )
    )
    fun index(): String = "Welcome to my simple banking application!"

    @Get(uri = "/customer/{id}", produces = ["application/json"])
    @Operation(
        summary = "Get a customer",
        description = "Get a customer by ID"
    )
    @ApiResponses(
        ApiResponse(
            description = "The customer corresponding to the ID",
            responseCode = "200",
        ),
        ApiResponse(
            description = "Customer not found",
            responseCode = "404",
        ),
    )
    // TODO: Convert Customer into Customer Response, mask SSN
    // TODO: Return error as JSON
    fun getCustomer(@PathVariable id: UUID) = customerService.get(id)
        ?: HttpResponse.notFound("Customer $id not found.")

    @Post(uri = "/customer", produces = ["application/json"], consumes = ["application/json"])
    @Operation(
        summary = "Create a customer",
        description = "Create a customer with basic information and no ID"
    )
    @ApiResponses(
        ApiResponse(
            description = "Successful creation of a new customer",
            responseCode = "201",
        ),
        ApiResponse(
            description = "Invalid customer creation request",
            responseCode = "400",
        ),
    )
    fun createCustomer(request: CreateCustomerRequest) =
        customerService.create(
            request.toCustomer()
        ).let {
            HttpResponse.created(
                CustomerResponse(
                    customerId = it.customerId,
                    firstName = it.firstName,
                    lastName = it.lastName,
                    middleName = it.middleName,
                    socialSecurityNumber = it.socialSecurityNumber
                        .substring(it.socialSecurityNumber.length - 4, it.socialSecurityNumber.length)
                        .padStart(8, 'X'),
                    uri = "/customer/${it.customerId}"
                ).also { response ->
                    println(response)
                }
            )
        }
}