package com.alicia.problems.com.alicia.problems.heap

class Heap(initialSize: Int) : PriorityQueue {

    // For this implementation, only positive numbers are supported
    // A -1 represents an empty position in the heap
    private var heap: Array<Int> = Array(initialSize) { -1 }
    private var size: Int = 0

    override fun findMin(): Int = heap[0]

    override fun getMin(): Int {

        // 1. Remove min
        // 2. Take last item and put at root and update size--
        // 3. Maintain heap property

        // TODO check sizes?
        val min = heap[0]

        if (size == 0) {
            return min
        }

        size -= 1
        heap[0] = heap[size - 1]
        // heap[size - 1] = -1  TODO move thigns up

        // TODO: maintain heap property
        var current = 0
        var left = (2 * current) + 1

        while (left < heap.size && heap[current] > heap[left]) {
            val swap = heap[current]
            heap[current] = heap[left]
            heap[left] = swap
            current = left
            left = (2 * current) + 1
        }

        return min
    }

    override fun insert(n: Int) {
        // Check for size
        heap[size] = n
        size += 1

        if (size >= heap.size) {
            // copy to a larger array
            this.heap = Array(heap.size + STEP) { index ->
                index.takeIf { it < heap.size }?.let { heap[it] } ?: -1
            }
        }

        // Maintain heap property by swapping up to parent if parent is greater
        var current = size - 1
        var parent = (current - 1) / 2

        while (parent >= 0 && heap[parent] > heap[current]) {
            val swap = heap[parent]
            heap[parent] = heap[current]
            heap[current] = swap
            current = parent
            parent = (current - 1) / 2
        }
    }

    override fun size(): Int {
        return size
    }

    companion object {

        private const val STEP = 10;

        fun heapify(numbers: List<Int>): PriorityQueue =
            Heap(numbers.size).let { heap ->
                numbers.forEach { heap.insert(it) }
                heap
            }
    }

}