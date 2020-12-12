package com.alicia.controller

import com.alicia.exceptions.GenericBadRequestException
import com.alicia.model.AddMemberRequest
import com.alicia.model.MemberResponse
import com.alicia.services.MemberService
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.mockito.Mockito.mock
import javax.inject.Inject

@MicronautTest
class MemberControllerTest {

    @Inject
    @field:Client("/member")
    lateinit var client: HttpClient

    @Inject
    lateinit var memberService: MemberService

    @MockBean(MemberService::class)
    fun memberService(): MemberService = mock(MemberService::class.java)


    @Test
    fun `WHEN create member THEN return created member`() {
        val addMemberRequest = AddMemberRequest(
                email = "person@people.com",
                firstName = "Jane",
                lastName = "Doe",
        )
        val expectedResponse  = MemberResponse(
                email = addMemberRequest.email,
                name = "${addMemberRequest.firstName} ${addMemberRequest.lastName}",
        )
        val request = HttpRequest.POST("", addMemberRequest)

        Mockito.`when`(memberService.addMember(addMemberRequest)).thenReturn(expectedResponse)

        val actualResponse = client.toBlocking()
                .retrieve(request, MemberResponse::class.java)

        assertEquals(expectedResponse, actualResponse)
    }

    @Test
    fun `WHEN create already existing member THEN return bad request`() {
        val addMemberRequest = AddMemberRequest(
                email = "person@people.com",
                firstName = "Jane",
                lastName = "Doe",
        )
        val request = HttpRequest.POST("", addMemberRequest)

        Mockito.`when`(memberService.addMember(addMemberRequest)).thenThrow(
                GenericBadRequestException()
        )

        val actualResponse = assertThrows<HttpClientResponseException> {
            client.toBlocking()
                    .retrieve(request, MemberResponse::class.java)
        }

        assertEquals(HttpStatus.BAD_REQUEST, actualResponse.status)
        assertEquals("Bad request", actualResponse.message)
    }

    @Test
    fun `WHEN create invalid member THEN return bad request`() {}

    @Test
    fun `WHEN get existing member THEN return member`() {}

    @Test
    fun `WHEN get non-existing member THEN return not found`() {}
}