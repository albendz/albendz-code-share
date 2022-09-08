package com.example.controller

import com.example.data.TransferStatus
import com.example.model.*
import com.example.service.CustomerService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import java.util.UUID

@Controller("/bank/transfer")
class TransferController(
) {

    @Get(uri = "/", produces = ["application/json"])
    fun getTransfers(@QueryValue(value = "status", defaultValue = "PENDING") status: TransferStatus): PaginatedTransferResponse =
        TODO("Unimplemented")

    @Post(uri = "/", produces = ["application/json"])
    fun createTransfer(request: CreateTransferRequest): TransferResponse =
        TODO("Unimplemented")

    @Post(uri = "/{transferId}", produces = ["application/json"])
    fun updateTransfer(@PathVariable transferId: UUID, request: UpdateTransferRequest): TransferResponse =
        TODO("Unimplemented")

    @Get(uri = "/{transferId}", produces = ["application/json"])
    fun getTransfer(@PathVariable transferId: UUID): TransferResponse =
        TODO("Unimplemented")
}