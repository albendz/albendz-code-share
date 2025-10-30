package org.albendz.share.mediaservice.controller.model

import java.net.URI
import java.util.UUID

object MediaModel {

    class Response(
        val mediaId: UUID,
        val mediaUrl: URI,
    )
}