package com.example.model

import com.example.data.Customer
import java.util.UUID

data class CustomerResponse(
    val customerId: UUID?,
    val uri: String,
    val firstName: String,
    val middleName: String?,
    val lastName: String,
    val socialSecurityNumber: String, // TODO: may need to be masked in response
) {
    constructor(customer: Customer) : this(
        customerId = customer.customerId,
        uri = "/customer/${customer.customerId}",
        firstName = customer.firstName,
        middleName = customer.middleName,
        lastName = customer.lastName,
        socialSecurityNumber = customer.socialSecurityNumber.let {
            it.substring(it.length - 4, it.length)
                .padStart(8, 'X')
        }
    )
}