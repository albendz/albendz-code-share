package com.alicia.problems.com.alicia.problems.heap

interface MinIntPriorityQueue {
    fun findMin(): Int
    fun getMin(): Int
    fun insert(n: Int)
    fun isEmpty(): Boolean
}