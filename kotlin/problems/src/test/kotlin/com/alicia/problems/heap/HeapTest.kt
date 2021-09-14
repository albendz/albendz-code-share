package com.alicia.problems.heap

import com.alicia.problems.com.alicia.problems.heap.Heap
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class HeapTest {

    @Test
    fun `WHEN init heap THEN heap has initial size no min`() {
        val heap = Heap(5)

        assertEquals(0, heap.size())
        assertEquals(-1, heap.findMin())
        assertEquals(-1, heap.getMin())
    }

    @Test
    fun `WHEN insert empty heap THEN size 1 and findMin inserted number`() {
        val heap = Heap(5)

        heap.insert(3)

        assertEquals(3, heap.findMin())
    }

    @Test
    fun `WHEN heapify increasing order list THEN findMin returns min`() {
        val heap = Heap.heapify(listOf(1, 2, 3, 4, 5))

        assertEquals(1, heap.findMin())
    }

    @Test
    fun `WHEN heapify decreasing order list THEN findMin returns min`() {
        val heap = Heap.heapify(listOf(5, 4, 3, 2, 1))

        assertEquals(1, heap.findMin())
    }

    @Test
    fun `WHEN get min THEN findMin returns new min`() {
        val heap = Heap.heapify(listOf(1, 2, 3, 4, 5))

        assertEquals(1, heap.getMin())
        assertEquals(2, heap.findMin())
    }

}