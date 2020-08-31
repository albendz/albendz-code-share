package com.kotlin.playground.conversation

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class Session {
    lateinit var startTime: Instant
    lateinit var endTime: Instant
    private var messages: MutableList<String> = mutableListOf()

    fun start() {
        startTime = Instant.now()
    }

    fun end() {
        endTime = Instant.now()
    }

    fun appendMessage(message: String) {
        messages.add(message)
    }

    fun summary(): String {
        val formatter = DateTimeFormatter.ofPattern("hh:mm a").withZone(ZoneId.systemDefault())
        return "Chat session started at ${formatter.format(startTime)} with a duration of " +
                "${endTime.minusMillis(startTime.toEpochMilli())} milliseconds with a total of ${messages.size} messages."
    }
}