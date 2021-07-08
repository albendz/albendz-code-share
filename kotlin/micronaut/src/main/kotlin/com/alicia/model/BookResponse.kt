package com.alicia.model

import com.alicia.constants.Availability
import com.fasterxml.jackson.annotation.JsonInclude
import java.util.UUID

@JsonInclude(JsonInclude.Include.ALWAYS)
data class BookResponse(
    val author: String?,
    val availableCopies: Int? = null,
    val genre: GenreResponse? = null,
    val isbn: String?,
    val publicationDate: String? = null,
    val title: String?,
    val totalCopies: Int? = null,
) {
    val availablity: Availability by lazy {
        if (availableCopies?.let { it > 0 } == true) {
            Availability.AVAILABLE
        } else {
            Availability.UNAVAILABLE
        }
    }
}

@JsonInclude(JsonInclude.Include.ALWAYS)
data class CopyResponse(
    val id: UUID?,
    val isbn: String?,
    val status: String?,
)

@JsonInclude(JsonInclude.Include.ALWAYS)
data class AuthorResponse(
    val firstName: String?,
    val lastName: String?,
    val dob: String?,
    val dod: String?,
    val biography: String?,
    val id: UUID?,
)

@JsonInclude(JsonInclude.Include.ALWAYS)
data class LoanResponse(
    val id: UUID?,
    val copyId: UUID?,
    val member: UUID?,
    val loanDate: String?,
    val loanLength: Int?,
)

@JsonInclude(JsonInclude.Include.ALWAYS)
data class HoldResponse(
    val id: UUID?,
    val isbn: String?,
    val holdDate: String?,
    val member: String?,
)
