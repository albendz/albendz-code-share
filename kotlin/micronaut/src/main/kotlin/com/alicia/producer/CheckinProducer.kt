package com.alicia.producer

import com.alicia.model.CheckinRecord
import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.KafkaKey
import io.micronaut.configuration.kafka.annotation.Topic

@KafkaClient
interface CheckinProducer {

    @Topic("\${library.kafka.checkin-topic}")
    fun sendCheckinBook(@KafkaKey isbn: String, record: CheckinRecord)

}