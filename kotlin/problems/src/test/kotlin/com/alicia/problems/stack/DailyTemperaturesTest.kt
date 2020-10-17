package com.alicia.problems.stack

import com.alicia.problems.com.alicia.problems.stack.DailyTemperatures
import com.alicia.problems.com.alicia.problems.stack.InvalidTemperatureException
import com.alicia.problems.com.alicia.problems.stack.TemperatureListTooLong
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class DailyTemperaturesTest {

    private val dailyTemperatures = DailyTemperatures()

    @Test
    fun `Leet code test case 1`() {
        val input = listOf(73, 74 , 75, 71, 69, 72, 76, 73)
        val expected = listOf(1, 1, 4, 2, 1, 1, 0, 0)

        val result = dailyTemperatures.getDailyTemperatures(input)

        compareLists(expected, result)
    }

    @Test
    fun `WHEN input all ascending THEN all ones`() {
        val input = listOf(73, 74, 75, 76, 77, 78, 79, 80)
        val expected = listOf(1, 1, 1, 1, 1, 1, 1, 0)

        val result = dailyTemperatures.getDailyTemperatures(input)

        compareLists(expected, result)
    }

    @Test
    fun `WHEN input all descending THEN all zeroes`() {
        val input = listOf(79, 78, 77, 76, 75, 74, 73, 72)
        val expected = listOf(0, 0, 0, 0, 0, 0, 0, 0)

        val result = dailyTemperatures.getDailyTemperatures(input)

        compareLists(expected, result)
    }

    @Test
    fun `WHEN all the same THEN all zeroes`() {
        val input = listOf(77, 77, 77, 77, 77, 77, 77, 77)
        val expected = listOf(0, 0, 0, 0, 0, 0, 0, 0)

        val result = dailyTemperatures.getDailyTemperatures(input)

        compareLists(expected, result)
    }

    @Test
    fun `WHEN ascending and descending THEN mixed output`() {
        val input = listOf(   50, 60, 70, 80, 70, 60, 50, 60, 70, 80, 70, 60, 50, 60, 70)
        val expected = listOf(1,   1,  1,  0,  5,  3,  1,  1,  1,  0,  0,  3,  1,  1, 0)

        val result = dailyTemperatures.getDailyTemperatures(input)

        compareLists(expected, result)
    }

    @Test
    fun `WHEN input temperature is out of range THEN throw exception`() {
        val belowInput = listOf(25, 45, 65)

        Assertions.assertThrows(InvalidTemperatureException::class.java) {
            dailyTemperatures.getDailyTemperatures(belowInput)
        }

        val aboveInput = listOf(250, 45, 65)

        Assertions.assertThrows(InvalidTemperatureException::class.java) {
            dailyTemperatures.getDailyTemperatures(aboveInput)
        }
    }

    @Test
    fun `WHEN empty temperatures return empty`(){
        val result = dailyTemperatures.getDailyTemperatures(listOf())

        assertTrue(result.isEmpty())
    }

    @Test
    fun `WHEN temperatures too long throw exception`() {
        val input = IntArray(30001) { 50 }.toList()

        Assertions.assertThrows(TemperatureListTooLong::class.java) {
            dailyTemperatures.getDailyTemperatures(input)
        }
    }

    private fun compareLists(listOne: List<Int>, listTwo: IntArray) {
        for(i in listOne.indices) {
            assertEquals(listOne[i], listTwo[i])
        }
    }
}