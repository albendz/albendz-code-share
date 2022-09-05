package com.example.model

import java.util.UUID

class AccountResponse(
    val id: UUID,
    val balance: Int = 0,
)