package com.alicia.data

import com.alicia.constants.Availability
import com.alicia.model.BookResponse
import java.util.Date
import javax.persistence.*

@Entity
@Table(name = "book")
data class Book (
    @Id
    @Column(name = "isbn")
    var isbn: String? = null,

    @ManyToOne(fetch = FetchType.EAGER)
    var author: Author? = null, // TODO: make non-null later

    @ManyToOne
    var genre: Genre? = null,

    @Temporal(value = TemporalType.DATE)
    @Column(name = "publication_date")
    var publicationDate: Date? = null,

    @Column(name = "title")
    var  title: String? = null,
) {

    @Transient
    var copies: List<Copy> = emptyList()

    fun toBookResponse(): BookResponse =
            BookResponse(
                author = "${author?.lastName}, ${author?.firstName}",
                genre = genre?.toGenreResponse(),
                isbn = isbn,
                publicationDate = null,
                title = title,
                availableCopies = copies.filter { it.status == Availability.AVAILABLE.name }.size,
                totalCopies = copies.size,
            )
}