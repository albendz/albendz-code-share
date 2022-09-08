package com.example.repository

import com.example.data.Customer
import jakarta.inject.Singleton
import java.util.UUID

@Singleton
class InMemoryCustomerRepository : CustomerRepository {

    private val map: MutableMap<UUID, Customer> = mutableMapOf()
    private val list: MutableList<Customer> = mutableListOf()

    override fun get(id: UUID): Customer? = map[id]

    override fun getAll(offset: Int, count: Int): List<Customer> =
        offset.takeIf { it < list.size }?.let {
            if ((offset + count) >= list.size) {
                list.subList(offset, list.size)
            } else {
                list.subList(offset, offset + count + 1)
            }
        } ?: emptyList()

    override fun save(customer: Customer): Customer = customer.let {
        map[it.customerId] = it
        list.add(it)
        it
    }

    override fun getBySocialSecurityNumber(ssn: String): Customer = list.first { it.socialSecurityNumber == ssn }

    override fun delete(id: UUID) {
        id.takeIf { map.containsKey(id) }?.let { list.remove(map.remove(id)) }
    }
}