package com.alicia
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest
class LibraryTest {

    @Inject
    lateinit var application: EmbeddedApplication<*>

    @Test
    fun `WHEN application is started THEN application is running`() {
        Assertions.assertTrue(application.isRunning)
    }
}
