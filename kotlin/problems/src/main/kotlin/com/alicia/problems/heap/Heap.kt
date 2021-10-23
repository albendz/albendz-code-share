package com.alicia.problems.com.alicia.problems.heap

// For this implementation, only positive numbers are supported
// A -1 represents an empty position in the heap

class Heap(initialSize: Int = STEP) : MinIntPriorityQueue {

    private var heap: Array<Int> = Array(initialSize) { -1 }
    private var size: Int = 0

    // Will return -1 if empty
    override fun findMin(): Int = this.heap[0]

    override fun getMin(): Int {
        val min = heap[0]

        if (size == 0) {
            return min
        }

        heap[0] = heap[size - 1]
        heap[size - 1] = -1
        size -= 1

        // Maintain heap property
        // Swap down if root is greater than children
        var current = 0
        var left = (2 * current) + 1

        while (left <= (this.size - 1) && this.heap[current] > this.heap[left]) {
            val swap = this.heap[current]
            this.heap[current] = this.heap[left]
            this.heap[left] = swap

            // Move down the heap
            current = left
            left = (2 * current) + 1
        }

        return min
    }

    override fun insert(n: Int) {
        this.heap[size] = n
        this.size += 1

        if (this.size >= this.heap.size) {
            this.expand()
        }

        // Maintain heap property
        // Swap up to parent if parent is greater than current
        var current = this.size - 1
        var parent = (current - 1) / 2

        while (parent >= 0 && this.heap[parent] > this.heap[current]) {
            // Swap
            val swap = this.heap[parent]
            this.heap[parent] = this.heap[current]
            this.heap[current] = swap

            // Move up the heap
            current = parent
            parent = (current - 1) / 2
        }
    }

    override fun isEmpty() = this.size > 0

    fun size(): Int {
        return this.size
    }

    private fun expand() {
        this.heap = Array(this.heap.size + STEP) { index ->
            index.takeIf { it < this.heap.size }?.let { this.heap[it] } ?: -1
        }
    }

    companion object {

        private const val STEP = 10;

        fun heapify(numbers: List<Int>): MinIntPriorityQueue =
            Heap(numbers.size).let { heap ->
                numbers.forEach { heap.insert(it) }
                heap
            }
    }

}