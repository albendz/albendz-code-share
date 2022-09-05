package com.example.model

data class PaginatedTransferResponse(
    val offset: Int,
    val count: Int,
    val transfers: List<TransferResponse>,
)