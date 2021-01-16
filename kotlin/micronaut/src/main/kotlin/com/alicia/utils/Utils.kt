package com.alicia.utils

object Utils {

    private val WHITESPACE = arrayOf(" ", "\n",  "\t")

    fun toGenreName(genre: String): String {
        return genre.split(*WHITESPACE).filter { it.isNotBlank() }
            .joinToString(separator = " ") {
                it.trim().toLowerCase().capitalize()
            }
    }

}
