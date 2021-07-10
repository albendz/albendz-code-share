package com.alicia.configuration

import io.micronaut.context.annotation.ConfigurationProperties

@ConfigurationProperties("mongo")
class MongoConfig {
    lateinit var connectionString: String
}