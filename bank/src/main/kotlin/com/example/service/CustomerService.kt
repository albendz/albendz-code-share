package com.example.service

import com.example.data.Customer
import com.example.repository.CustomerRepository
import jakarta.inject.Singleton
import java.util.UUID

@Singleton
class CustomerService(
    private val customerRepository: CustomerRepository,
) {
    fun get(id: UUID): Customer? = customerRepository.get(id)
    fun getAll(offset: Int = 0, count: Int = 10): List<Customer> = customerRepository.getAll(offset, count)
    fun create(customer: Customer): Customer = customerRepository.save(customer)
    fun delete(id: UUID) = customerRepository.delete(id)
}