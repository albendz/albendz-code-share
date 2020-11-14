package com.alicia.model

import com.alicia.data.Author
import com.alicia.data.Book
import com.alicia.data.Genre
import java.util.*

data class AddBookRequest(
        val authorId: UUID?,
        val title: String,
        val publicationDate: Long? = null,
        val genre: String? = null,
        val isbn: String,
) {
    fun toBook(genre: Genre?): Book =
            Book(
                    author = Author(id = authorId),
                    title = title,
                    genre = null,
                    publicationDate = publicationDate?.let { Date(publicationDate) },
                    isbn = isbn,
            )
}
