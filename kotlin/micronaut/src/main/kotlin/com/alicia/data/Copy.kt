package com.alicia.data

import com.alicia.model.CopyResponse
import java.util.UUID
import javax.persistence.*

@Entity
@Table(name = "copy")
data class Copy(
    @Id
    @Column(name = "copy_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    var copyId: UUID? = null,

    @ManyToOne
    var book: Book? = null,

    @Column(name = "status")
    var status: String? = null
) {
    fun toCopyResponse(): CopyResponse = CopyResponse(
        id = copyId,
        isbn = book?.isbn,
        status = status,
    )
}
