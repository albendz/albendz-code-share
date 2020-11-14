package com.alicia.data

import com.alicia.model.AuthorResponse
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "author")
data class Author (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    val id: UUID? = null,

    @Column(name = "first_name")
    val firstName: String? = null,

    @Column(name = "last_name")
    val lastName: String? = null,

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
                    id = id,
                    firstName = firstName,
                    lastName = lastName,
                    dob = birthDate?.let { DateTimeFormatter.ISO_DATE.withZone( ZoneId.of("UTC")).format(it.toInstant()) },
                    dod = deathDate?.let { DateTimeFormatter.ISO_DATE.withZone( ZoneId.of("UTC")).format(it.toInstant()) },
                    biography = biography?.let { String(it) },
            )
}
