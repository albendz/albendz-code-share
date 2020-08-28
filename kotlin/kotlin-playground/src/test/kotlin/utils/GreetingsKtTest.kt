package utils

import com.kotlin.playground.utils.goodbye
import com.kotlin.playground.utils.hello
import com.kotlin.playground.utils.hour
import com.kotlin.playground.utils.instantToHour
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.junit.platform.commons.util.StringUtils.isBlank
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

internal class GreetingsKtTest {

    @Test fun `hello() returns expected value`() {
        assertEquals("Hello!", hello())
    }

    @Test fun `goodbye() returns expected value`() {
        assertEquals("Goodbye!", goodbye())
    }

    @Test fun `hour() returns non-empty String`() {
        assertFalse(isBlank(hour()))
    }

    @Test fun `instantToHour() returns formatted time given`() {
        val now = Instant.now()
        val formatter = DateTimeFormatter.ofPattern("hh:mm a").withZone(ZoneId.systemDefault())

        val expected = "It is ${formatter.format(now)}"
        val actual = instantToHour(now)

        assertEquals(expected, actual)
    }
}