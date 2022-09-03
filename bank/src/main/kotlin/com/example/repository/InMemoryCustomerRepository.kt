package com.example.repository

import com.example.data.Customer
import jakarta.inject.Singleton
import java.util.UUID

@Singleton
class InMemoryCustomerRepository : CustomerRepository {

    private val map: MutableMap<UUID, Customer> = mutableMapOf()

    override fun get(id: UUID): Customer? = map[id]

    override fun save(customer: Customer): Customer = customer.let {
        map[it.customerId] = it
        it
    }

    override fun getBySocialSecurityNumber(ssn: String): Customer = TODO("Not yet implemented")
}