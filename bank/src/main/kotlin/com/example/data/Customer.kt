package com.example.data

import java.util.UUID

data class Customer(
    val customerId: UUID,
    val firstName: String,
    val middleName: String?,
    val lastName: String,
    val socialSecurityNumber: String,
)
