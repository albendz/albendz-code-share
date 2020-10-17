package com.alicia.problems.com.alicia.problems.stack

import java.util.*

class MinStack {
    private val stack: Stack<Int> = Stack()
    private val minStack: Stack<Int> = Stack()

    fun push(x: Int) {
        stack.push(x)

        if (minStack.empty()) minStack.push(x)
        else {
            val top = minStack.peek()
            if (top < x) minStack.push(top)
            else minStack.push(x)
        }
    }

    fun pop(): Int? {
        return if (stack.empty()) null
        else  {
            minStack.pop()
            stack.pop()
        }
    }

    fun peek(): Int? {
        return if (stack.empty()) null
        else stack.peek()
    }

    fun getMin(): Int? {
        return if (minStack.empty()) null
        else minStack.peek()
    }
}