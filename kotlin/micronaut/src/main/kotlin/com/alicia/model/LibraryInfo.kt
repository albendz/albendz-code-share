package com.alicia.model

data class LibraryInfo(
        val libraryName: String,
        val foundedYear: String,
        val location: Location
)

data class Location(
        val city: String,
        val state: String,
        val address: String,
        val country: String,
        val zipcode: String,
)