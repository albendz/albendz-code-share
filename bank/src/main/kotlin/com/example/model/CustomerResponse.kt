package com.example.model

import java.util.UUID

data class CustomerResponse(
    val customerId: UUID?,
    val uri: String,
    val firstName: String,
    val middleName: String?,
    val lastName: String,
    val socialSecurityNumber: String, // TODO: may need to be masked in response
)