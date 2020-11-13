package com.alicia.model

import com.fasterxml.jackson.annotation.JsonInclude
import java.util.*

@JsonInclude(JsonInclude.Include.ALWAYS)
data class BookResponse(
        val author: String?,
        val title: String?,
        val publicationDate: String? = null,
        val genre: GenreResponse? = null,
        val isbn: String?,
)

@JsonInclude(JsonInclude.Include.ALWAYS)
data class GenreResponse(
        val name: String?
)

@JsonInclude(JsonInclude.Include.ALWAYS)
data class CopyResponse(
        val id: UUID?,
        val isbn: String?,
        val status: String?,
        val metadata: Map<String, List<String>>?,
)

@JsonInclude(JsonInclude.Include.ALWAYS)
data class AuthorResponse(
        val firstName: String?,
        val lastName: String?,
        val dob: String?,
        val dod: String?,
        val biography: String?,
)

@JsonInclude(JsonInclude.Include.ALWAYS)
data class LoanResponse(
        val id: UUID?,
        val copyId: UUID?,
        val member: String?,
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

@JsonInclude(JsonInclude.Include.ALWAYS)
data class MemberResponse(
        val name: String?,
        val overdueFees: Int?,
)