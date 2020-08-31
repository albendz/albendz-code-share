@file:JvmName("Main")
package com.kotlin.playground

import com.kotlin.playground.conversation.Chatbot
import com.kotlin.playground.utils.goodbye
import com.kotlin.playground.utils.hello
import com.kotlin.playground.utils.hour

fun main() {
    println(hello())
    println(hour())
    println(Chatbot.start())
    println(goodbye())
}