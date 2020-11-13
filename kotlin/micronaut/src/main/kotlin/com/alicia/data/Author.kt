package com.alicia.data

import com.alicia.model.AuthorResponse
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "author")
data class Author (
    @EmbeddedId
    val authorName: Name,

    @Temporal(value = TemporalType.DATE)
    @Column(name = "birth_date")
    var birthDate: Date? = null,

    @Temporal(value = TemporalType.DATE)
    @Column(name = "death_date")
    var deathDate: Date? = null,

    @Lob
    @Column(name = "biography")
    var biography: ByteArray? = null,
) {
    fun toAuthorResponse(): AuthorResponse =
            AuthorResponse(
                    firstName = authorName.firstName,
                    lastName = authorName.lastName,
                    dob = birthDate?.let { DateTimeFormatter.ISO_DATE.format(it.toInstant()) },
                    dod = deathDate?.let { DateTimeFormatter.ISO_DATE.format(it.toInstant()) },
                    biography = "$biography",
            )
}
