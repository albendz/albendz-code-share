package com.alicia.model

import com.alicia.data.Book
import java.util.*

data class AddBookRequest(
        val author: UUID? = null,
        val title: String,
        val publicationDate: Long? = null,
        val genre: String? = null,
        val isbn: String,
) {
    fun toBook(): Book =
            Book(
                    title = title,
                    genre = genre,
                    publicationDate = publicationDate?.let { Date(publicationDate) },
                    status = "NEW",
                    isbn = isbn
            )
}
