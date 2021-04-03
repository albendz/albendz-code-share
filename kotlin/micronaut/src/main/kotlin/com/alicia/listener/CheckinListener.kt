package com.alicia.listener

import com.alicia.model.CheckinRecord
import io.micronaut.configuration.kafka.annotation.KafkaKey
import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.Topic
import io.micronaut.messaging.annotation.Body

@KafkaListener(groupId = "library-checkin-group")
class CheckinListener {

    @Topic("\${library.kafka.checkin-topic}")
    fun receiveCheckin(@KafkaKey isbn: String, @Body checkinRecord: CheckinRecord) {
        // TODO: handle checkin
        print("Checkin")
    }
}