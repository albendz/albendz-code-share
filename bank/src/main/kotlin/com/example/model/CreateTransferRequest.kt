package com.example.model

import java.util.UUID

data class CreateTransferRequest(
    val toAccount: UUID,
    val fromAccount: UUID,
    val amount: Int,
)
