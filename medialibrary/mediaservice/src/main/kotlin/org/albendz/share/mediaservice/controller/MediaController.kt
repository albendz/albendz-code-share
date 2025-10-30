package org.albendz.share.mediaservice.controller

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Produces
import org.albendz.share.mediaservice.controller.model.MediaModel
import java.net.URI
import java.util.UUID

@Controller("/media")
class MediaController {

    @Get("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getMedia(@PathVariable id: UUID) = MediaModel.Response(id, URI.create("https://s3.amazon.aws.com/media/123"))

}