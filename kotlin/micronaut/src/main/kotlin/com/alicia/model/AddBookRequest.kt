package com.alicia.model

import com.alicia.constants.Availability
import com.alicia.data.Author
import com.alicia.data.Book
import com.alicia.data.Copy
import com.alicia.data.Genre
import com.alicia.utils.Utils
import java.util.Date
import java.util.UUID

data class AddBookRequest(
    val authorId: UUID?,
    val title: String,
    val publicationDate: Long? = null,
    val genre: String? = null,
    val isbn: String,
    val desiredCopies: Int = 0,
) : ValidatableRequest() {

    fun toBook(): Book =
        Book(
            author = Author(id = authorId),
            title = title,
            genre = genre?.let { g ->
                val genreName = Utils.toGenreName(g)
                Genre(name = genreName)
            },
            publicationDate = publicationDate?.let { Date(publicationDate) },
            isbn = isbn,
        ).apply {
            copies = List(desiredCopies) { Copy(status = Availability.AVAILABLE.name) }
        }

    override fun getErrors(): List<String> {
        val errors = mutableListOf<String>()

        if (genre?.isBlank() == true) {
            errors.add("Genre")
        }

        if (authorId == null) {
            errors.add("Author")
        }

        if (isbn.isBlank()) {
            errors.add("ISBN")
        }

        if (title.isBlank()) {
            errors.add("Title")
        }

        if (desiredCopies < 0) {
            errors.add("Desired Copies")
        }

        return errors
    }
}
