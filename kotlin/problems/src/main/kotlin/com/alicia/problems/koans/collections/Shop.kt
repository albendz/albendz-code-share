package com.alicia.problems.com.alicia.problems.koans.collections

data class Shop(val name: String, val customers: List<Customer>)

data class Customer(val name: String, val city: City, val orders: List<Order>)

data class Order(val products: List<Product>, val isDelivered: Boolean)

data class Product(val name: String, val price: Double)

data class City(val name: String)

object Extensions {

    fun Shop.getUniqueCitiesForCustomer(): Set<City> =
        this.customers.map { it.city }.toSet()

    fun Shop.getCustomersByName(): Map<String, Customer> =
        this.customers.associateBy { name }

    fun Shop.getCustomerWithMaxDeliveredOrders(): Customer? =
        this.customers.maxByOrNull {
            it.orders.filter { order -> order.isDelivered }.size
        }

    fun Shop.getCustomerMostDeliveredOrders(): List<Customer> =
        this.customers.sortedByDescending { it.orders.size }

    fun Customer.customerOrderSequence(): Sequence<Order> =
        this.orders.asSequence()

}