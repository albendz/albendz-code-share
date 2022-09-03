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
    fun create(customer: Customer): Customer = customerRepository.save(customer)
}