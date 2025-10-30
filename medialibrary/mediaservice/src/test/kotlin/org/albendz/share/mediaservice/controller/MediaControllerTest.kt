package org.albendz.share.mediaservice.controller

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import java.util.UUID

class MediaControllerTest {

    private val controller = MediaController()

    @Test
    fun `WHEN get media THEN media returned`() {
        val id = UUID.randomUUID()

        val response = controller.getMedia(id)

        assertEquals(id, response.mediaId)
        assertNotNull(response.mediaUrl)
    }
}