package com.example.repository

import com.example.data.Customer
import java.util.UUID

interface CustomerRepository {
    fun get(id: UUID): Customer?
    fun save(customer: Customer): Customer
    fun getBySocialSecurityNumber(ssn: String): Customer
}