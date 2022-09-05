package com.example.model

import com.example.data.TransferStatus
import java.util.UUID

data class TransferResponse(
    val id: UUID,
    val sourceAccount: UUID,
    val destinationAccount: UUID,
    val amount: UUID,
    val status: TransferStatus,
)