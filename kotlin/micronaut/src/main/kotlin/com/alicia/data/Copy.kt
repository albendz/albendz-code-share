package com.alicia.data

import com.alicia.model.BookResponse
import com.alicia.model.CopyResponse
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "copy")
data class Copy (
    @Id
    @Column(name = "copy_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    var copyId: UUID? = null,

    @ManyToOne
    var book: Book? = null,

    @Column(name = "status")
    var status: String? = null,

    @Lob
    @Column(name = "metadata")
    var metadata: ByteArray? = null,
) {
    fun toCopyResponse(): CopyResponse = CopyResponse(
            id = copyId,
            isbn = book?.isbn,
            status = status,
            metadata = ObjectMapper().readValue(metadata, MetadataTypeReference()),
    )
}

class MetadataTypeReference: TypeReference<Map<String, List<String>>>()