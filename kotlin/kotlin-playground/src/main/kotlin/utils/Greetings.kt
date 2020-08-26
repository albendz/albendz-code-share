package com.kotlin.playground.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter


    fun hello() : String = "Hello!"
    fun goodbye() : String = "Goodbye!"
    fun time(): String {
        var formatter = DateTimeFormatter.ofPattern("hh:mm a").withZone(ZoneId.systemDefault())
        return "It is " + formatter.format(Instant.now())
    }