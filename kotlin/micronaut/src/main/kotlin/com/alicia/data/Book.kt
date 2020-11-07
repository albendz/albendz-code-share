package com.alicia.data

import com.alicia.model.BookResponse
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "book")
data class Book (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var bookId: UUID? = null,

    @Column(name = "title")
    var  title: String? = null,

    var publicationDate: Date? = null,

    var genre: String? = null,

    var status: String? = null, // Available, checked out, unavailable

    var isbn: String? = null,

    @ManyToOne
    var author: Author? = null,
) {
    fun toBookResponse(): BookResponse = BookResponse(
            id = bookId,
            title = title,
            author = "${author?.lastName}, ${author?.firstName}",
            publicationDate = publicationDate?.toInstant()?.toEpochMilli(),
            genre = genre,
            status = status,
            isbn = isbn,
    )
}