package com.alicia.model

import com.alicia.data.Author
import com.alicia.data.Book
import com.alicia.data.Genre
import java.util.Date
import java.util.UUID

data class AddBookRequest(
    val authorId: UUID?,
    val title: String,
    val publicationDate: Long? = null,
    val genre: String? = null,
    val isbn: String,
) : ValidatableRequest() {

    fun toBook(genre: Genre?): Book =
        Book(
            author = Author(id = authorId),
            title = title,
            genre = genre,
            publicationDate = publicationDate?.let { Date(publicationDate) },
            isbn = isbn,
            copies = emptyList()
        )

    override fun getErrors(): List<String> {
        val errors = mutableListOf<String>()

        if (authorId == null) {
            errors.add("Author ID")
        }

        if (isbn.isBlank()) {
            errors.add("ISBN")
        }

        if (title.isBlank()) {
            errors.add("Title")
        }

        return errors
    }
}
