package com.alicia.data

import com.alicia.model.CopyResponse
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Lob
import javax.persistence.ManyToOne
import javax.persistence.Table

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
    var status: String? = null,

    @Lob
    @Column(name = "metadata")
    var metadata: ByteArray? = null,
) {
    fun toCopyResponse(): CopyResponse = CopyResponse(
        id = copyId,
        isbn = book?.isbn,
        status = status,
        metadata = null,
    )
}

private class MetadataTypeReference : TypeReference<Map<String, List<String>>>()