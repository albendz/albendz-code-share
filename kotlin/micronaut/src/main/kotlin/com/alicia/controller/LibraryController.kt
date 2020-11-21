package com.alicia.controller

import com.alicia.configuration.LibraryInfoConfiguration
import com.alicia.model.LibraryInfo
import com.alicia.model.Location
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import javax.inject.Inject

@Controller("/library")
class LibraryController {

    @Inject
    lateinit var libraryInfoConfiguration: LibraryInfoConfiguration

    @Get("/info")
    fun getLibraryInfo(): LibraryInfo = LibraryInfo(
            libraryName = libraryInfoConfiguration.libraryName,
            foundedYear = libraryInfoConfiguration.foundedYear,
            Location(
                    city = libraryInfoConfiguration.city,
                    address = libraryInfoConfiguration.address,
                    zipcode = libraryInfoConfiguration.zipCode,
                    state = libraryInfoConfiguration.state,
                    country = libraryInfoConfiguration.country
            )
    )
}