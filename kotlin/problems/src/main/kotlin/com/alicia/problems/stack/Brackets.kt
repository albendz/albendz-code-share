package com.alicia.problems.com.alicia.problems.stack

import java.util.*

class Brackets {

    companion object {
        private const val TAB = 9
        private const val NEWLINE = 10
    }

    @Throws(InvalidCharacterException::class)
    fun matchedBrackets(input: String): Boolean{
        val stack = Stack<Char>()
        val openToClose = mapOf('(' to ')', '[' to ']', '{' to '}')
        val closeToOpen = mapOf(')' to '(', ']' to '[', '}' to '{')
        val whitespace = setOf(TAB.toChar(), ' ', NEWLINE.toChar())

        input.toCharArray().forEach { c ->
            when (c) {
                in openToClose.keys -> stack.push(c)
                in closeToOpen.keys -> {
                    if (stack.empty()) return false

                    stack.peek().let {
                        if(closeToOpen[c] != it) return false
                        else stack.pop()
                    }
                }
                in whitespace -> Unit
                else -> throw InvalidCharacterException()
            }
        }

        return stack.empty()
    }
}

class InvalidCharacterException: Exception("Non-bracket, non-whitespace character found.")