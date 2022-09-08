package com.example.repository

import com.example.data.Customer
import java.util.UUID

interface CustomerRepository {
    fun get(id: UUID): Customer?
    fun getAll(offset: Int, count: Int): List<Customer>
    fun save(customer: Customer): Customer
    fun getBySocialSecurityNumber(ssn: String): Customer
    fun delete(id: UUID): Unit
}