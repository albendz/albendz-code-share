package com.example.controller

import com.example.model.CreateCustomerRequest
import com.example.model.CustomerResponse
import com.example.model.PaginatedCustomerResponse
import com.example.service.CustomerService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import java.util.UUID

@Controller("/bank/customer")
class CustomerController(
    private val customerService: CustomerService
) {

    @Get(uri = "/{id}", produces = ["application/json"])
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

    @Post(uri = "/", produces = ["application/json"], consumes = ["application/json"])
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
                    it
                ).also { response ->
                    println(response)
                }
            )
        }

    @Get("/")
    fun searchCustomers(
        @QueryValue(value = "offset", defaultValue = "0") offset: Int,
        @QueryValue(value = "count", defaultValue = "10") count: Int,
    ): PaginatedCustomerResponse = PaginatedCustomerResponse(offset, count, customerService.getAll(offset, count).map { CustomerResponse(it) })

    @Post("/{id}")
    fun updateAsPost(@PathVariable id: UUID): CustomerResponse = TODO("Unimplemented")

    @Put("/{id}")
    fun updateAsPut(@PathVariable id: UUID): CustomerResponse = TODO("Unimplemented")

    @Delete("/{id}")
    fun delete(@PathVariable id: UUID): Unit = customerService.delete(id)
}