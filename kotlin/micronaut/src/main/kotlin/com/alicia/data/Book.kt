package com.alicia.data

import com.alicia.model.BookResponse
import java.time.format.DateTimeFormatter
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "book")
data class Book (
    @Id
    @Column(name = "isbn")
    var isbn: String? = null,

    @ManyToOne
    @Column(name = "author")
    var author: Author? = null,

    @ManyToOne
    @Column(name = "genre")
    var genre: Genre? = null,

    @Column(name = "publication_date")
    var publicationDate: Date? = null,

    @Column(name = "title")
    var  title: String? = null,
) {
    fun toBookResponse(): BookResponse =
            BookResponse(
                author = "${author?.authorName?.lastName}, ${author?.authorName?.firstName}",
                genre = genre?.toGenreResponse(),
                isbn = isbn,
                publicationDate = publicationDate?.let { DateTimeFormatter.ISO_DATE.format(it.toInstant()) },
                title = title,
            )
}