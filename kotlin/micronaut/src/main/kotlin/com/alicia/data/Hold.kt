package com.alicia.data

import com.alicia.model.HoldResponse
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "name")
data class Hold (
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id")
        val id: UUID? = null,

        @ManyToOne
        val book: Book? = null,

        @Temporal(value = TemporalType.DATE)
        @Column(name = "hold_date")
        val holdDate: Date? = null,

        @ManyToOne
        val member: Member? = null,
) {
        fun toHoldResponse(): HoldResponse =
                HoldResponse(
                        id = id,
                        isbn = book?.isbn,
                        holdDate = holdDate?.let { DateTimeFormatter.ISO_DATE.withZone( ZoneId.of("UTC")).format(it.toInstant()) },
                        member = "${member?.lastName}, ${member?.firstName}",
                )
}