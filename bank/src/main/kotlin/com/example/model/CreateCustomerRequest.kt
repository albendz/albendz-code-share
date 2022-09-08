package com.example.model

import com.example.data.Customer
import java.util.UUID

data class CreateCustomerRequest(
    val firstName: String,
    val middleName: String?,
    val lastName: String,
    val socialSecurityNumber: String,
) {
    fun toCustomer(): Customer = Customer(
        UUID.randomUUID(),
        firstName,
        middleName,
        lastName,
        socialSecurityNumber
    )
}
