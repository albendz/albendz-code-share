package com.alicia.problems.com.alicia.problems.stack

import java.util.*

class Brackets {
    fun matchedBrackets(input: String): Boolean {
        val stack = Stack<Char>()
        val openToClose = mapOf('(' to ')', '[' to ']', '{' to '}')
        val closeToOpen = mapOf(')' to '(', ']' to '[', '}' to '{')

        input.toCharArray().forEach {c ->
            if( c in openToClose.keys) stack.push(c)
            else if(c in closeToOpen.keys) {
                if (stack.empty()) return false

                stack.peek().let {
                    if(closeToOpen[c] != it) return false
                    else stack.pop()
                }
            }
        }

        return stack.empty()
    }
}