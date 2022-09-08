package com.example.model

import com.example.data.TransferStatus
import java.util.UUID

data class UpdateTransferRequest(
    val id: UUID,
    val status: TransferStatus,
)
