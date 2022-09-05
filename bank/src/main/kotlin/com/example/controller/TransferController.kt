package com.example.controller

import com.example.model.CreateCustomerRequest
import com.example.model.CustomerResponse
import com.example.model.PaginatedTransferResponse
import com.example.model.TransferResponse
import com.example.service.CustomerService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import java.util.UUID

@Controller("/bank/transfer")
class TransferController(
    private val customerService: CustomerService
) {

    @Get(uri = "/", produces = ["text/plain"])
    @ApiResponses(
        ApiResponse(
            description = "Display bank welcome message",
            responseCode = "200",
        )
    )
    fun getTransfers(@QueryValue(value = "status", defaultValue = "PENDING") status: TransferResponse): PaginatedTransferResponse =
        TODO("Unimplemented")

}