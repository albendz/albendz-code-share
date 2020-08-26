package utils

import com.kotlin.playground.utils.goodbye
import com.kotlin.playground.utils.hello
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class GreetingsKtTest {

    @Test
    fun testhHllo() {
        assertEquals("Hello!", hello())
    }

    @Test
    fun testGoodbye() {
        assertEquals("Goodbye!", goodbye())
    }

    @Test
    fun hour() {
    }

    @Test
    fun instantToHour() {
    }
}