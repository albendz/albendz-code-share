package com.alicia.configuration

import io.micronaut.context.annotation.ConfigurationProperties

@ConfigurationProperties("libraryInfo")
class LibraryInfoConfiguration {

    lateinit var libraryName: String
    lateinit var foundedYear: String
    lateinit var city: String
    lateinit var state: String
    lateinit var address: String
    lateinit var country: String
    lateinit var zipcode: String

}