package com.alicia.problems.com.alicia.problems.stack

import java.lang.Exception
import java.util.*

class DailyTemperatures {

    fun getDailyTemperatures(temperatures: List<Int>): IntArray {
        if (temperatures.size > 30000) throw TemperatureListTooLong()
        if (temperatures.isEmpty()) return IntArray(0)

        if (temperatures.any { it > 100 || it < 30}) throw InvalidTemperatureException()

        val maxStack = Stack<Int>()
        val result = IntArray(temperatures.size){ 0 }

        for(i in temperatures.size - 1 downTo 0 step 1) {
            val candidate = temperatures[i]

            if  (!maxStack.empty()) {
                var top = maxStack.peek()

                if(temperatures[top] > candidate) {
                    result[i] = top - i
                } else {
                    while (!maxStack.empty() && temperatures[top] <= candidate) {
                        maxStack.pop()
                        if (!maxStack.isEmpty()) top = maxStack.peek()
                    }

                    if (maxStack.isEmpty()) result[i] = 0
                    else result[i] = top - i
                }
            }

            maxStack.push(i)
        }

        return result
    }
}

class InvalidTemperatureException: Exception("Invalid temperature")

class TemperatureListTooLong: Exception("Temperature list above 30000")