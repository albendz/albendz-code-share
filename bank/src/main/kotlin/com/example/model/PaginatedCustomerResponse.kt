package com.example.model

class PaginatedCustomerResponse(
    val offset: Int,
    val count: Int,
    val customers: List<CustomerResponse>,
)