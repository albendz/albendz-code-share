package com.alicia.controller

import com.alicia.model.LibraryInfo
import com.alicia.model.Location
import com.alicia.model.PaginatedBookResponse
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

import javax.inject.Inject

@MicronautTest
class LibraryControllerTest {

    @Inject
    @field:Client("/library")
    lateinit var client: HttpClient

    @Test
    fun `WHEN get library info THEN return library info`() {
        val request = HttpRequest.GET<LibraryInfo>("/info")
        val expectedInfo = LibraryInfo(
                    libraryName = "MOMA",
                    foundedYear = "2020",
                    location = Location(
                    city = "New York",
                    state = "NY",
                    address = "11  W 53rd St.",
                    country = "USA",
                    zipcode =  "10019",
                )
        )

        val response = client.toBlocking().retrieve(request, LibraryInfo::class.java)

        assertEquals(expectedInfo, response)
    }
}