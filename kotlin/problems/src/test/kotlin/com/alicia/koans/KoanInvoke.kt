package com.alicia.koans

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class KoanInvoke {

    @Test
    fun `Koan Invoke Test`() {
        val invokableA = Invokable();
        val invokableB = Invokable();

        assertEquals(5, invokableA()()()()().numberOfInvocations, "Invokable A invoke times should be 5")
        assertEquals(3, invokableB()()().numberOfInvocations)
    }
}

class Invokable {
    var numberOfInvocations: Int = 0
        private set

    operator fun invoke(): Invokable {
        this.numberOfInvocations++
        return this
    }
}
