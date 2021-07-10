package com.alicia.factory

import com.alicia.configuration.MongoConfig
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import io.micronaut.context.annotation.Factory
import javax.inject.Singleton
import org.bson.codecs.configuration.CodecRegistries.fromProviders
import org.bson.codecs.configuration.CodecRegistries.fromRegistries
import org.bson.codecs.configuration.CodecRegistry
import org.bson.codecs.pojo.PojoCodecProvider


@Factory
class MongoDbClientFactory(private val configuration: MongoConfig) {

    @Singleton
    fun getMongoDbClient(): MongoClient {
        val pojoCodecRegistry: CodecRegistry = fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            fromProviders(PojoCodecProvider.builder().automatic(true).build())
        )

        val settings = MongoClientSettings.builder()
            .applyConnectionString(ConnectionString(configuration.connectionString))
            .codecRegistry(pojoCodecRegistry)
            .build()

        return MongoClients.create(settings)
    }
}