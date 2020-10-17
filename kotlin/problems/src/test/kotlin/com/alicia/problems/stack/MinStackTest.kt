package com.alicia.problems.stack

import com.alicia.problems.com.alicia.problems.stack.MinStack
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MinStackTest {
    lateinit var minStack: MinStack

    @BeforeEach
    fun setup() {
        minStack = MinStack()
    }

    @Test
    fun `Leetcode example`() {
        minStack.push(-2)
        minStack.push(0)
        minStack.push(-3)

        assertEquals(-3, minStack.getMin())
        assertEquals(-3, minStack.pop())
        assertEquals(0, minStack.peek())
        assertEquals(-2, minStack.getMin())
    }

    @Test
    fun `WHEN push value THEN value is returned on peek, pop, getMin`() {
        minStack.push(5)

        assertEquals(5, minStack.getMin())
        assertEquals(5, minStack.peek())
        assertEquals(5, minStack.pop())
    }

    @Test
    fun `WHEN empty stack THEN operations return null`() {
        assertNull(minStack.getMin())
        assertNull(minStack.peek())
        assertNull(minStack.pop())
    }

    @Test
    fun `WHEN ascending pushes THEN first is returned as min`() {
        minStack.push(0)
        minStack.push(1)
        minStack.push(2)
        minStack.push(3)
        minStack.push(4)
        minStack.push(5)

        assertEquals(0, minStack.getMin())
    }

    @Test
    fun `WHEN all values equal THEN value is returned`() {
        minStack.push(0)
        minStack.push(0)
        minStack.push(0)
        minStack.push(0)
        minStack.push(0)
        minStack.push(0)

        assertEquals(0, minStack.getMin())
    }

    @Test
    fun `WHEN descending pushes THEN top is returned as min`() {
        minStack.push(0)
        minStack.push(-1)
        minStack.push(-2)
        minStack.push(-3)
        minStack.push(-4)
        minStack.push(-5)

        assertEquals(-5, minStack.getMin())
    }

    @Test
    fun `WHEN min popped THEN min is updated`() {
        minStack.push(0)
        minStack.push(-1)
        minStack.push(-2)

        assertEquals(-2, minStack.getMin())
        minStack.pop()
        assertEquals(-1, minStack.getMin())
    }
}