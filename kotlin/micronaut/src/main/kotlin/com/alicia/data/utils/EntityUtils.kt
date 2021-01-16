package com.alicia.data.utils

object EntityUtils {
    fun toGenreName(genre: String): String {

        // TODO: sci-fi -> Sci-fi
        // young adult => Young Adult
        // FICTION -> Fiction
        // Title case

        return genre.capitalize()
    }
}